package com.admission.counselor.domain

import android.content.Context
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

enum class ThermalPolicy {
    FULL_SPEED,
    THROTTLED,
    DEGRADED
}

class EngineBusyException(message: String) : Exception(message)

@Singleton
class LlmEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sessionMutex = Mutex()
    private var inferenceEngine: LlmInference? = null
    private var activeChannel: Channel<String>? = null
    private var modelPath: String? = null
    
    private var thermalPolicy = ThermalPolicy.FULL_SPEED

    /**
     * Set the path to the model file resolved from Play Asset Delivery.
     */
    suspend fun loadModel(path: String) = withContext(LlmThread.dispatcher) {
        modelPath = path
    }

    /**
     * Updates the thermal policy from the system listener.
     */
    fun setThermalPolicy(policy: ThermalPolicy) {
        this.thermalPolicy = policy
    }

    /**
     * Executes the formatted prompt against the model. Yields chunks as they are generated.
     * Throws [EngineBusyException] immediately if the engine is already generating.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun generateResponse(prompt: String): Flow<String> = flow {
        if (!sessionMutex.tryLock()) {
            throw EngineBusyException("Counselor is busy formulating a response")
        }
        val startTime = System.currentTimeMillis()
        var firstTokenReceived = false
        var lastText = ""
        try {
            val engine = getOrInitializeEngine()
            val channel = Channel<String>(Channel.UNLIMITED)
            activeChannel = channel

            // Start streaming inference
            engine.generateResponseAsync(prompt)

            for (token in channel) {
                lastText = token
                if (!firstTokenReceived) {
                    val ttft = System.currentTimeMillis() - startTime
                    android.util.Log.d("LlmEngineDiagnostics", "Time to First Token (TTFT): ${ttft}ms")
                    firstTokenReceived = true
                }
                emit(token)
                
                // If thermal state is throttled, introduce a 40ms pause between tokens
                if (thermalPolicy == ThermalPolicy.THROTTLED) {
                    kotlinx.coroutines.delay(40)
                }
            }
            
            val totalTime = System.currentTimeMillis() - startTime
            val estimatedTokenCount = (lastText.length / 4).coerceAtLeast(1)
            val throughput = if (totalTime > 0) (estimatedTokenCount.toFloat() / (totalTime.toFloat() / 1000f)) else 0f
            android.util.Log.d("LlmEngineDiagnostics", "Generation completed: $estimatedTokenCount tokens estimated in ${totalTime}ms (Throughput: ${String.format("%.2f", throughput)} tokens/sec)")
        } finally {
            activeChannel = null
            sessionMutex.unlock()
        }
    }.flowOn(LlmThread.dispatcher)

    /**
     * Cancels active generation by closing the token channel and
     * closing the native instance (since MediaPipe lacks a native cancel API).
     */
    suspend fun cancelGeneration() = withContext(LlmThread.dispatcher) {
        val channel = activeChannel
        if (channel != null) {
            channel.close(CancellationException("User cancelled generation"))
            activeChannel = null
            inferenceEngine?.close()
            inferenceEngine = null
            // Proactively re-initialize engine in background to mitigate cold start latency
            try {
                getOrInitializeEngine()
            } catch (e: Exception) {
                android.util.Log.e("LlmEngineDiagnostics", "Failed to proactively re-initialize engine: ${e.message}")
            }
        }
    }

    /**
     * Unloads the engine from RAM to free native resources.
     */
    suspend fun close() = withContext(LlmThread.dispatcher) {
        activeChannel?.close(CancellationException("Model unloading"))
        activeChannel = null
        inferenceEngine?.close()
        inferenceEngine = null
    }

    private fun getOrInitializeEngine(): LlmInference {
        val path = modelPath ?: throw IllegalStateException("Model path has not been set.")
        
        if (inferenceEngine == null) {
            val options = LlmInference.LlmInferenceOptions.builder()
                .setModelPath(path)
                .setMaxTokens(4096)
                .setResultListener { partialResult, done ->
                    val currentChannel = activeChannel
                    if (currentChannel != null) {
                        currentChannel.trySend(partialResult)
                        if (done) {
                            currentChannel.close()
                        }
                    }
                }
                .build()
            
            inferenceEngine = LlmInference.createFromOptions(context, options)
            android.util.Log.d("LlmEngineDiagnostics", "LiteRT-LM engine initialized")
        }
        return inferenceEngine!!
    }
}
