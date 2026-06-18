# Walkthrough: Sprint 3 (On-Device RAG & Context Management)

This walkthrough demonstrates the completion of Sprint 3, linking the database generated in Sprint 2 to the Android application layer.

## Room Database Setup
*   **Asset Loading**: Updated `AppModule.kt` to load `admission.db` directly from the `assets/` directory using `.createFromAsset()`. We also enforced a destructive migration fallback since this is a read-only dataset that gets fully replaced on updates.
*   **FTS5 Search**: Defined `CourseEntity` and `CourseFtsEntity` to map to our SQLite schema. Built `CourseDao.kt` containing an optimized query (`MATCH :query`) that searches the virtual FTS5 table and limits results to the top 3 hits.

## Domain Layer Logic
*   **RAGRetrievalManager**: Implemented keyword extraction that translates user queries into SQLite `MATCH` syntax (`"keyword"* OR "keyword"*`). It retrieves the top 3 matches and formats them into a clean string.
*   **PromptInjector**: Constructed the exact instruction prompt structure required by the Gemma model. It successfully wraps the system instructions, the retrieved context string, and the raw user query inside the `<start_of_turn>` and `<end_of_turn>` tokens.

## UI Orchestration
*   **CounselorViewModel**: Implemented the `ViewModel` using Coroutines to orchestrate the flow. When `onUserQuery()` is called, it triggers the RAG retrieval, formats the system prompt, and updates the `uiState`. 
*   *Note: In the current iteration, it displays the generated prompt string. In Sprint 4, this string will be piped directly into the LiteRT-LM engine.*

## Tests
*   Stood up `RAGRetrievalManagerTest.kt` for `IT-RAG-001` verification to ensure our Prompt string building and FTS5 querying logic aligns with expectations.
