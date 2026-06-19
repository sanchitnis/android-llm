package com.admission.counselor.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * Dedicated thread management for the LLM Engine.
 * Ensures that heavy inference operations do not block the Main thread,
 * and runs sequentially on a single thread.
 */
object LlmThread {
    private var executor = createExecutor()
    
    val dispatcher: CoroutineDispatcher
        get() {
            synchronized(this) {
                if (executor.isShutdown) {
                    executor = createExecutor()
                }
                return executor.asCoroutineDispatcher()
            }
        }

    private fun createExecutor() = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "LlmThread").apply { isDaemon = true }
    }

    fun destroy() {
        synchronized(this) {
            executor.shutdown()
        }
    }
}

