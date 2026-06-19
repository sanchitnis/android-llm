# Walkthrough: Sprint 6 (Security, Profiling, & Optimization)

This walkthrough documents the completion of final performance optimizations, thermal throttling mitigations, and context memory pruning features for the Admission Counselor app.

**Document Date**: June 18, 2026

---

## 1. Summary of Changes

We implemented system-level resource and thermal safeguards, ensuring the 2.58 GB local model executes stably on mid-range hardware. The following files were created or modified:

1.  **[LlmEngine.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/domain/LlmEngine.kt) [MODIFY]**: Added the `ThermalPolicy` enum. Implemented dynamic delegate switching (ML Drift GPU vs. XNNPACK CPU) and a 40ms token delay for warm states. Programmed diagnostic Logcat outputs for Time to First Token (TTFT) and average throughput.
2.  **[CounselorViewModel.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/CounselorViewModel.kt) [MODIFY]**: Registered a system `PowerManager` status listener. Programmed custom memory trimming logic for moderate, low, and critical memory pressure alerts.
3.  **[LlmEngineConcurrencyTest.kt](file:///d:/Github/android-llm/app/src/androidTest/java/com/admission/counselor/LlmEngineConcurrencyTest.kt) [MODIFY]**: Expanded Instrumented test stubs to cover memory trim history reductions and thermal policy states.

---

## 2. Technical Optimization Details

### 2.1 Thermal Throttling Mitigation
We integrated the system `PowerManager` thermal status API (available since Android 10 without special permissions):
*   **Listener**: Inside [CounselorViewModel](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/CounselorViewModel.kt), we listen to thermal changes and map them:
    *   `THERMAL_STATUS_NONE` / `LIGHT` -> `FULL_SPEED`.
    *   `THERMAL_STATUS_MODERATE` -> `THROTTLED`.
    *   `THERMAL_STATUS_SEVERE` or higher -> `DEGRADED`.
*   **Delays**: If the policy is throttled, the engine inserts a 40ms pause between each generated token. This dampens GPU cycles and lowers the device temperature.
*   **Dynamic Delegate Fallback**: If the policy drops to degraded, the engine closes the active native JNI instance and rebuilds it using the `LlmInference.Backend.CPU` delegate (utilizing XNNPACK thread pools) instead of the GPU backend. The switch executes between user queries to avoid active streaming interrupts.

### 2.2 Memory Trim Context Pruning
Under system memory constraints, the app dynamically prunes the active Kotlin StateFlow chat history turns to prevent process termination:
*   `TRIM_MEMORY_RUNNING_MODERATE`: Prunes the list of messages down to the last 2 conversation turns (a total of 4 ChatMessage items: 2 user queries and 2 responses). This shrinks the active KV cache prompt context.
*   `TRIM_MEMORY_RUNNING_LOW`: Clears the conversation history completely from RAM, resetting the prompt window.
*   `TRIM_MEMORY_RUNNING_CRITICAL` / `TRIM_MEMORY_COMPLETE`: Immediately cancels generation, closes the engine, and frees all native memory allocations.

### 2.3 Latency Diagnostics Logging
We added profiling checkpoints to monitor execution speeds. The engine measures timestamps and logs diagnostics to Logcat under the tag `LlmEngineDiagnostics`:
*   **Time to First Token (TTFT)**: Logs the interval between starting inference and receiving the first token segment (target < 0.5s GPU / < 1.5s CPU).
*   **Generation Throughput**: Calculates and prints the total tokens generated divided by time upon completion (target > 15 t/s GPU / > 5 t/s CPU).

---

## 3. Verification Test Coverage

Test stubs were written in [LlmEngineConcurrencyTest.kt](file:///d:/Github/android-llm/app/src/androidTest/java/com/admission/counselor/LlmEngineConcurrencyTest.kt) to verify:
1.  **Memory Trim Pruning**: Validates that calling the trim callback with moderate pressure prunes the active message count.
2.  **Thermal Transitions**: Asserts the engine correctly transitions its internal policy when the thermal status updates.
