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
    private val executor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "LlmThread").apply { isDaemon = true }
    }
    
    val dispatcher: CoroutineDispatcher = executor.asCoroutineDispatcher()

    fun destroy() {
        executor.shutdown()
    }
}

