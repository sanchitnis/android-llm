package com.admission.counselor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.admission.counselor.domain.PromptInjector
import com.admission.counselor.domain.RAGRetrievalManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounselorViewModel @Inject constructor(
    private val ragManager: RAGRetrievalManager,
    private val promptInjector: PromptInjector
) : ViewModel() {

    private val _uiState = MutableStateFlow<String>("Ready to Chat")
    val uiState: StateFlow<String> = _uiState

    fun onUserQuery(query: String) {
        viewModelScope.launch {
            _uiState.value = "Searching Database..."
            
            // 1. Retrieve RAG Context
            val context = ragManager.retrieveContext(query)
            
            // 2. Inject Prompt
            val finalPrompt = promptInjector.formatTurn(query, context)
            
            // 3. For Sprint 3, we just output the formatted prompt to UI/Log
            _uiState.value = "Prompt Generated:\n\n$finalPrompt"
            
            // Sprint 4 will pass finalPrompt to the LiteRT-LM Engine
        }
    }
}
