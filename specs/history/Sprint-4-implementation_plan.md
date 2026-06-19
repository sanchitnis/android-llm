# Sprint 4: LiteRT-LM Engine & Concurrency

This implementation plan focuses on the core AI functionality of the app: integrating the LiteRT-LM (MediaPipe/TFLite) engine to run the `gemma-4-E2B-it` model entirely offline.

## User Review Required

> [!WARNING]
> **Dependency Stubs**: Since I do not have access to the physical model file (`gemma-4-E2B-it.litertlm`) or the proprietary internal Google LiteRT-LM binaries for Android on this command-line environment, I will implement the engine using standard MediaPipe/LiteRT task library APIs.
> **Question**: Is it acceptable that I stub out the actual compilation of the native C++ inference library if we encounter build errors without the binary dependencies, focusing entirely on a perfect Kotlin implementation?

> [!IMPORTANT]
> **Thread Lifecycle**: The specifications require the `LlmEngine` to unload from RAM after 5 minutes of idle time. I propose using a Kotlin Coroutine `delay(300_000)` inside the ViewModel that cancels and nullifies the `LlmEngine` instance, tracked by a `StateFlow`.

## Proposed Changes

### 1. LiteRT-LM Dependencies
#### [MODIFY] `app/build.gradle.kts`
- Add dependencies for Google AI Edge / LiteRT Tasks.

### 2. The Inference Engine
#### [NEW] `app/src/main/java/com/admission/counselor/domain/LlmEngine.kt`
- Implements the wrapper around `LlmInference` (or `LlmTaskRunner` depending on the exact SDK version used).
- Features functions: `loadModel(path)`, `generateResponse(prompt)`, and `close()`.

### 3. Concurrency and Threading
#### [NEW] `app/src/main/java/com/admission/counselor/domain/LlmThread.kt`
- Creates a dedicated background coroutine dispatcher (`Dispatchers.IO` or a single-thread custom dispatcher).
- Utilizes a `Mutex` to block concurrent calls. If the user sends a second message while the engine is generating, the first generation must be cancelled, or the second must queue (the specs mandate blocking overlapping calls, so I will implement a queue/cancel mechanism).

### 4. ViewModel Integration
#### [MODIFY] `app/src/main/java/com/admission/counselor/ui/CounselorViewModel.kt`
- Connect the `AssetDeliveryManager` to ensure the model path is passed to `LlmEngine.loadModel()`.
- Send the `PromptInjector.formatTurn()` output directly into `LlmEngine.generateResponse()`.
- Expose the generated tokens sequentially to the UI via a `StateFlow`.
- Implement the 5-minute idle unload timer.

## Verification Plan

### Automated Tests
- I will write unit tests mocking the `LlmInference` class to ensure that the `Mutex` properly blocks overlapping calls and the idle timer triggers `close()`.

### Manual Verification
- We will execute the application and confirm the sequence: Play Asset Delivery triggers -> Model Loads into Memory -> RAG fetches context -> Prompt is injected -> UI receives mocked tokens (if binary is missing) or actual tokens.
