package com.admission.counselor.ui

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.PowerManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.admission.counselor.data.ModelAssetLoader
import com.admission.counselor.domain.EngineBusyException
import com.admission.counselor.domain.LlmEngine
import com.admission.counselor.domain.LlmThread
import com.admission.counselor.domain.PromptInjector
import com.admission.counselor.domain.RAGRetrievalManager
import com.admission.counselor.domain.ThermalPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CounselorUiState {
    object Idle : CounselorUiState
    data class LoadingModel(val progress: Float) : CounselorUiState
    object Ready : CounselorUiState
    data class Generating(val partialText: String) : CounselorUiState
    data class ModelUnloaded(val reason: String) : CounselorUiState
    data class Error(val message: String) : CounselorUiState
}

data class ChatMessage(
    val content: String,
    val isUser: Boolean
)

@HiltViewModel
class CounselorViewModel @Inject constructor(
    private val ragManager: RAGRetrievalManager,
    private val promptInjector: PromptInjector,
    private val llmEngine: LlmEngine,
    private val modelAssetLoader: ModelAssetLoader,
    @ApplicationContext private val context: Context
) : ViewModel(), DefaultLifecycleObserver, ComponentCallbacks2 {

    private val _uiState = MutableStateFlow<CounselorUiState>(CounselorUiState.Idle)
    val uiState: StateFlow<CounselorUiState> = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _streamingText = MutableStateFlow("")
    val streamingText: StateFlow<String> = _streamingText.asStateFlow()

    private var activeJob: Job? = null
    private var idleTimerJob: Job? = null
    private var backgroundUnloadJob: Job? = null
    
    private var thermalListener: PowerManager.OnThermalStatusChangedListener? = null

    init {
        // Observe process lifecycle events
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        // Register for memory trim callbacks
        context.registerComponentCallbacks(this)

        // Setup thermal status listener if supported (API 29+)
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        if (powerManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val listener = PowerManager.OnThermalStatusChangedListener { status ->
                val policy = when (status) {
                    PowerManager.THERMAL_STATUS_NONE,
                    PowerManager.THERMAL_STATUS_LIGHT -> ThermalPolicy.FULL_SPEED
                    PowerManager.THERMAL_STATUS_MODERATE -> ThermalPolicy.THROTTLED
                    else -> ThermalPolicy.DEGRADED // Severe, Critical, Emergency, Shutdown
                }
                llmEngine.setThermalPolicy(policy)
                android.util.Log.d("CounselorViewModel", "Thermal status changed: $status -> mapped to policy: $policy")
            }
            powerManager.addThermalStatusListener(context.mainExecutor, listener)
            thermalListener = listener
        }

        // Resolve model path and set it in the engine
        viewModelScope.launch {
            modelAssetLoader.resolveModelPath()
                .onSuccess { path ->
                    viewModelScope.launch {
                        llmEngine.loadModel(path)
                    }
                    _uiState.value = CounselorUiState.Ready
                }
                .onFailure { error ->
                    _uiState.value = CounselorUiState.Error(error.message ?: "Failed to locate model pack")
                }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMessage = ChatMessage(text, isUser = true)
        _messages.update { it + userMessage }

        // Cancel any active generation before starting a new one
        activeJob?.cancel()
        viewModelScope.launch {
            llmEngine.cancelGeneration()
        }

        idleTimerJob?.cancel() // Suspend idle countdown during generation

        activeJob = viewModelScope.launch {
            _uiState.value = CounselorUiState.LoadingModel(0.0f)
            _streamingText.value = ""

            // Ensure the model is initialized
            modelAssetLoader.resolveModelPath()
                .onSuccess { path ->
                    llmEngine.loadModel(path)
                }
                .onFailure { error ->
                    _uiState.value = CounselorUiState.Error(error.message ?: "Model file missing")
                    resetIdleTimer()
                    return@launch
                }

            _uiState.value = CounselorUiState.Generating("")
            
            // Search database for relevant university context
            val contextData = ragManager.retrieveContext(text)
            val finalPrompt = promptInjector.formatTurn(text, contextData)

            llmEngine.generateResponse(finalPrompt)
                .catch { e ->
                    if (e is EngineBusyException) {
                        _uiState.value = CounselorUiState.Error("Counselor is busy formulating a response")
                    } else {
                        _uiState.value = CounselorUiState.Error(e.message ?: "Generation error occurred")
                    }
                }
                .onCompletion {
                    resetIdleTimer()
                }
                .collect { token ->
                    _uiState.value = CounselorUiState.Generating(token)
                    _streamingText.value = token
                }

            // Commit completed response
            val finalText = _streamingText.value
            if (finalText.isNotEmpty()) {
                _messages.update { it + ChatMessage(finalText, isUser = false) }
                _streamingText.value = ""
                _uiState.value = CounselorUiState.Ready
            }
        }
    }

    fun stopGeneration() {
        activeJob?.cancel()
        viewModelScope.launch {
            llmEngine.cancelGeneration()
        }
        val partialText = _streamingText.value
        if (partialText.isNotEmpty()) {
            _messages.update { it + ChatMessage(partialText + " [cancelled]", isUser = false) }
            _streamingText.value = ""
        }
        _uiState.value = CounselorUiState.Ready
        resetIdleTimer()
    }

    fun reloadModel() {
        viewModelScope.launch {
            _uiState.value = CounselorUiState.LoadingModel(0.0f)
            modelAssetLoader.resolveModelPath()
                .onSuccess { path ->
                    llmEngine.loadModel(path)
                    _uiState.value = CounselorUiState.Ready
                    resetIdleTimer()
                }
                .onFailure { error ->
                    _uiState.value = CounselorUiState.Error(error.message ?: "Failed to reload model")
                }
        }
    }

    private fun resetIdleTimer() {
        idleTimerJob?.cancel()
        idleTimerJob = viewModelScope.launch {
            // Unload the engine from RAM after 5 minutes of inactivity
            delay(300_000)
            llmEngine.close()
            _uiState.value = CounselorUiState.ModelUnloaded("Inactivity timeout exceeded")
        }
    }

    // ProcessLifecycleObserver implementation
    override fun onStart(owner: LifecycleOwner) {
        // App returned to foreground, cancel any pending background unload
        backgroundUnloadJob?.cancel()
    }

    override fun onStop(owner: LifecycleOwner) {
        // App moved to background, trigger unload if sustained for 30 seconds
        backgroundUnloadJob?.cancel()
        backgroundUnloadJob = viewModelScope.launch {
            delay(30_000)
            llmEngine.close()
            _uiState.value = CounselorUiState.ModelUnloaded("App backgrounded")
        }
    }

    // ComponentCallbacks2 implementation
    override fun onTrimMemory(level: Int) {
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE -> {
                // Prune conversation history to the last 2 turns (4 messages total)
                _messages.update { currentList ->
                    if (currentList.size > 4) currentList.takeLast(4) else currentList
                }
                android.util.Log.d("CounselorViewModel", "Memory running moderate: pruned history to last 2 turns.")
            }
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW -> {
                // Clear conversation history from memory entirely
                _messages.update { emptyList() }
                android.util.Log.d("CounselorViewModel", "Memory running low: cleared conversation history from RAM.")
            }
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL,
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                // Force unload the model
                stopGeneration()
                viewModelScope.launch {
                    llmEngine.close()
                }
                _uiState.value = CounselorUiState.ModelUnloaded("Low memory pressure")
                android.util.Log.d("CounselorViewModel", "Memory running critical: closed engine and unloaded model.")
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {}
    override fun onLowMemory() {}

    override fun onCleared() {
        super.onCleared()
        ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
        context.unregisterComponentCallbacks(this)
        
        // Remove thermal status listener
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        if (powerManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            thermalListener?.let {
                powerManager.removeThermalStatusListener(it)
            }
        }
    }
}
