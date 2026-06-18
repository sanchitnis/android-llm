# Open Issues & Design Decisions

This document tracks unresolved issues, design decisions, architectural gaps, and tradeoffs identified during the specification process.

## Issue Tracker

| ID | Phase | Description | Status | Resolution / Notes |
| :--- | :--- | :--- | :--- | :--- |
| ISS-001 | Phase 0 | Confirm official conversion path and support status for `gemma-4-E2B-it` in LiteRT-LM. | CLOSED | Confirmed support via `.litertlm` quantized format from LiteRT-LM converter tools. |
| ISS-002 | Phase 0 | Determine optimal strategy for packaging/distributing the 2.58 GB model file (e.g., Play Asset Delivery vs. post-install background downloader). | REVISED | Originally: `on-demand` PAD. Revised to `install-time` PAD to eliminate INTERNET permission requirement (see ISS-009). |
| ISS-003 | Phase 0 | Establish standard performance budget (RAM, TTFT - Time To First Token, tokens/sec) on target mid-range Android hardware. | CLOSED | Budgets defined in specs/PRD.md: TTFT <0.5s (GPU) / <1.5s (CPU), throughput >15 t/s (GPU) / >5 t/s (CPU), peak RAM 2.0 GB. |
| ISS-004 | Phase 2 | Analyze mechanism for canceling active text generation in LiteRT-LM (since `generateAsync` lacks a cancel method). | REVISED | Original resolution was incomplete. Revised: `cancelGeneration()` method added to `LocalLlmEngine` that closes the active channel and reinitializes the engine instance (see ISS-008). |
| ISS-005 | Phase 3 | Establish how updates to admission database records (fees, deadlines) are distributed to the app, given the offline-first mandate. | CLOSED | Database templates are packaged inside app assets, copied to storage on first run, and updated by shipping standard app updates through the Google Play Store. |
| ISS-006 | Phase 5 | Decide if student conversation history should persist across app restarts (requires local storage) or exist only in-memory (wiped on app close). | CLOSED | Resolved in specs/SECURITY-AND-PRIVACY.md: Conversation histories are stored exclusively in-memory using Kotlin StateFlow and wiped on app close, idle timeout, or memory unload to protect student privacy. |

## Architecture Review Findings (Phase 13)

| ID | Severity | Finding | Status | Resolution |
| :--- | :--- | :--- | :--- | :--- |
| ISS-007 | CRITICAL | `generateResponse` Channel never closes on success; `for (token in channel)` suspends indefinitely, holding the Mutex forever. | CLOSED | Added completion sentinel callback (`done` flag). Channel now explicitly closes on generation completion. |
| ISS-008 | CRITICAL | Cancelling the ViewModel Job does not cancel native LiteRT-LM generation. Tokens keep firing into an unread channel. | CLOSED | Added `cancelGeneration()` method to `LocalLlmEngine` that closes channel and reinitializes engine. ViewModel calls it before starting new requests. |
| ISS-009 | CRITICAL | Zero-network-permission mandate contradicts `on-demand` PAD which requires `INTERNET` permission. | CLOSED | Switched PAD to `install-time` delivery in BUILD-GUIDE.md. No network access needed post-install. |
| ISS-010 | HIGH | Per-token `_messages` list copy in ViewModel creates O(N^2) GC pressure at 15 tokens/sec. | CLOSED | Replaced with `_streamingText` MutableStateFlow accumulator. Final text committed to list only on generation completion. |
| ISS-011 | HIGH | Raw user text passed to FTS5 MATCH allows operator injection and poor natural language matching. | CLOSED | Added query sanitization (strip FTS5 operators, remove stopwords, quote tokens) and `bm25()` ranking in COUNSELOR-CONTEXT-SCHEMAS.md. |
| ISS-012 | HIGH | `newSingleThreadContext` creates a thread that is never closed, leaking on engine recreation. | CLOSED | Replaced with `Executors.newSingleThreadExecutor().asCoroutineDispatcher()`. Added `destroy()` method to close executor. Called from `ViewModel.onCleared()`. |
| ISS-013 | HIGH | Model path hardcoded to `/data/user/0/.../files/` but PAD resolves to a different dynamic path. | CLOSED | Model path is now a constructor parameter (`modelPath: String`) resolved dynamically from `ModelAssetLoader.resolveModelPath()`. |
| ISS-014 | MEDIUM | Spec references `onPause` engine "pause" but `LlmInference` has no pause API. Unloading on every `onStop` causes poor UX during screen rotations. | CLOSED | Replaced with `ProcessLifecycleOwner` detection. Idle countdown starts on `ON_STOP`, immediate unload only after 30s in STOPPED state. |
| ISS-015 | MEDIUM | 2048 context window is too restrictive. Only 348 tokens for model output. | CLOSED | Expanded to 4096 tokens. Generation budget increased to 1546 tokens. KV-cache overhead is ~200 MB, well within 2 GB ceiling. |
| ISS-016 | MEDIUM | `System.gc()` is unreliable and can cause 200-500ms UI freezes during full GC. | CLOSED | Removed `System.gc()` call. ART's concurrent GC handles JVM heap. Native memory freed by `LlmInference.close()` destructor. |
| ISS-017 | MEDIUM | `HardwarePropertiesManager.getDeviceTemperatures()` requires system app privileges on API 29+. | CLOSED | Replaced with `PowerManager.getThermalStatus()` (API 29+) and `addThermalStatusListener()` in MODEL-RUNTIME-POLICY.md. |
| ISS-018 | MEDIUM | SQLCipher adds ~10 MB APK size and 5-15% query overhead for encrypting publicly available admission data. | CLOSED | Replaced with Android File-Based Encryption (FBE). Room `createFromAsset()` used directly. No application-level encryption overhead. |
| ISS-019 | MEDIUM | No Dependency Injection framework specified; unit test mocking will be difficult. | CLOSED | Added Hilt DI dependencies in BUILD-GUIDE.md. `CounselorViewModel` annotated with `@HiltViewModel` and `@Inject constructor`. |
| ISS-020 | MEDIUM | Prompt template crams system instructions, RAG context, and history into a single `user` turn, reducing model comprehension. | CLOSED | Rewrote prompt template with proper Gemma multi-turn `<start_of_turn>` / `<end_of_turn>` formatting in COUNSELOR-CONTEXT-SCHEMAS.md. |

