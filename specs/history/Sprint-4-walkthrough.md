# Walkthrough: Sprint 4 (LiteRT-LM Engine & Concurrency)

This walkthrough documents the completion of the core AI engine and concurrency components for the Admission Counselor app.

**Document Date**: June 18, 2026

---

## 1. Summary of Changes

We implemented the production-ready inference engine, dedicated background thread management, dynamic asset loading, and active lifecycle coordination. The following source files were created or modified:

1.  **[build.gradle.kts](file:///d:/Github/android-llm/app/build.gradle.kts)**: Added the `androidx.lifecycle:lifecycle-process` dependency to support background state monitoring.
2.  **[ModelAssetLoader.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/data/ModelAssetLoader.kt)**: Implemented path resolution using the Play Core Asset Delivery APIs for the install-time asset pack.
3.  **[LlmThread.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/domain/LlmThread.kt)**: Configured a dedicated single-thread executor dispatcher to isolate all native library calls on a single daemon background thread.
4.  **[LlmEngine.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/domain/LlmEngine.kt)**: Implemented the wrapper around MediaPipe Tasks GenAI `LlmInference`. Added `Mutex` tryLock busy rejection and channel-based callback-to-flow translation.
5.  **[CounselorViewModel.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/CounselorViewModel.kt)**: Programmed the state machine that drives UI states (`Idle`, `LoadingModel`, `Ready`, `Generating`, `ModelUnloaded`, `Error`), the 5-minute inactivity unloader, 30-second sustained background unloader, and the `onTrimMemory` garbage collection hooks.

---

## 2. Technical Architecture Walkthrough

```
[User Query] -> [CounselorViewModel] -> [ModelAssetLoader] (Resolves Path)
                       |
                       +-> [RAGRetrievalManager] (FTS5 search)
                       |
                       +-> [PromptInjector] (Gemma multi-turn wrapping)
                       |
                       +-> [LlmEngine] (Acquires Mutex and starts generateResponse)
                                 |
                                 +-> [LlmThread] (Executes natively on daemon thread)
                                           |
                                           v
                             [MediaPipe Tasks GenAI C++ Engine]
```

### 2.1 Dynamic Asset Path Resolution
The model asset is bundled as an install-time Play Asset Delivery pack. [ModelAssetLoader](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/data/ModelAssetLoader.kt) queries `AssetPackManager` to resolve the platform-specific directory where the file is unpacked. This is passed directly to the engine, resolving hardcoded file path issues.

### 2.2 Dedicated Concurrency Threading
Because native C++ libraries are sensitive to multi-threaded accesses, [LlmThread](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/domain/LlmThread.kt) confines execution. A dedicated single-thread dispatcher executes all loads, generations, and unloads sequentially.

### 2.3 Mutex Lock and Immediate Rejection
[LlmEngine](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/domain/LlmEngine.kt) uses `sessionMutex.tryLock()` before starting. If the user initiates a second query while the model is generating, the engine rejects it immediately and throws an `EngineBusyException`. This notifies the user that the counselor is busy without queuing commands or crashing the native context.

### 2.4 Token Flow Routing
MediaPipe's streaming API executes asynchronously. To translate JNI callbacks to Kotlin Coroutine flows, we pipe characters through an unbuffered channel (`Channel.UNLIMITED`). When the JNI done flag is set, the channel closes. If a JNI error occurs, the channel is closed with the target throwable.

### 2.5 Active Memory Lifecycle Guard
In [CounselorViewModel](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/CounselorViewModel.kt), we manage memory through three triggers:
1.  **Inactivity Timer**: Triggers `llmEngine.close()` and unloads the model if no user messages are received for 5 minutes.
2.  **Sustained Background**: Uses `ProcessLifecycleOwner` to detect when the application is backgrounded. Initiates a 30-second delay countdown. If the user returns within 30 seconds (e.g. screen rotation, answering a call), the countdown cancels to avoid reload lag. If backgrounded for more than 30 seconds, it unloads the model.
3.  **Low Memory (LOMEM)**: Listens to Android system `onTrimMemory` broadcasts. Forces an immediate generation cancel and model unload if critical memory limits are reached.

---

## 3. Next Steps

Sprint 4 is complete. The engine, threading model, and background lifecycles are implemented. In Sprint 5, we will build the user interface using Jetpack Compose, adhering to REVA University brand colors and Glacial Indifference typography.
