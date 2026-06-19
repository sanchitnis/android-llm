# Sprint 6: Security, Profiling, & Optimization

This implementation plan focuses on final optimizations and security diagnostics: implementing thermal listener status mapping using the Android PowerManager API, building dynamic GPU-to-CPU backend fallback policies under extreme heat, optimizing KV-cache history context sizes under system memory pressure, and capturing detailed execution diagnostics (Time to First Token and generation throughput).

---

## User Review Required

> [!IMPORTANT]
> **Dynamic Delegate Fallback**: Switching execution delegates (from GPU acceleration to CPU fallback) requires closing the active native `LlmInference` instance and re-initializing it with the new configuration. Since this takes 2 to 5 seconds, switching during active text generation would cause a noticeable lag. I propose checking the thermal state before starting generation so that backend switches only happen between user queries, preventing streaming interruptions.

---

## Proposed Changes

### 1. Thermal Listening and Delegate Fallback
#### [MODIFY] [LlmEngine.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/domain/LlmEngine.kt)
- Support re-instantiating the `LlmInference` delegate. Maintain state variables to select either the ML Drift GPU delegate or XNNPACK CPU delegate.
- Set a generation token delay flag (e.g. introduce a 40ms wait delay between generated tokens) when the thermal policy is throttled.
- Implement token-level diagnostics logging: record the start time of generation, output the Time to First Token (TTFT), and calculate output tokens per second upon stream completion.

### 2. Lifecycle Thermal Bindings and Memory Pruning
#### [MODIFY] [CounselorViewModel.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/CounselorViewModel.kt)
- Register a thermal listener with the system `PowerManager` (API 29+) on initialization. Map incoming status values (`THERMAL_STATUS_MODERATE` to `THERMAL_STATUS_SEVERE`) to engine policies.
- In `onTrimMemory`:
  - On `TRIM_MEMORY_RUNNING_MODERATE`: Prune conversation history to the last 2 turns, shrinking the active prompt context.
  - On `TRIM_MEMORY_RUNNING_LOW`: Clear the conversation history list completely from RAM.

### 3. Concurrency and Optimization Diagnostic Tests
#### [MODIFY] [LlmEngineConcurrencyTest.kt](file:///d:/Github/android-llm/app/src/androidTest/java/com/admission/counselor/LlmEngineConcurrencyTest.kt)
- Implement test cases validating that the thermal policy transitions execute correctly.
- Add test assertions checking that memory trim triggers successfully prune the view model message list history.

---

## Verification Plan

### Automated Tests
- Run tests in the instrumented test directory asserting that calling `onTrimMemory` with moderate pressure successfully reduces the chat list size, and severe thermal thresholds toggle the engine configurations.

### Manual Verification
- Deploy to a physical Android device. Under sustained generation, review the Logcat diagnostic outputs to confirm that the TTFT (Time to First Token) is under 0.5s (GPU) / 1.5s (CPU), throughput values are printed, and the 40ms token delay engages under high temperatures.
