package com.admission.counselor.domain

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptInjector @Inject constructor() {

    fun formatTurn(userQuery: String, contextString: String): String {
        // Follows the Gemma model instruction format
        return """
            <start_of_turn>system
            You are a polite, accurate Admission Counselor for REVA University.
            Answer the user's query strictly based on the provided context below.
            If the answer is not in the context, say "I don't have that information. Please contact the admissions office."
            
            [CONTEXT START]
            $contextString
            [CONTEXT END]
            <end_of_turn>
            <start_of_turn>user
            $userQuery
            <end_of_turn>
            <start_of_turn>model
        """.trimIndent()
    }
}
