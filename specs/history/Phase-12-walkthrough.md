# Walkthrough: Phase 12 (Windows Research Lock)

This walkthrough documents the completion of **Phase 12: Windows Research Lock** for the Admission Counselor AI.

**Document Date**: June 21, 2026

---

## 1. Summary of Changes

We expanded and finalized the local model execution and runtime specifications for the Windows target in the active specs directory. The following files were created or modified:

1. **[WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md) [MODIFY]**: Expanded Section 4 with comprehensive parameters for dynamic linking via JNI, DX12 DirectML adapter options, Gemini API Cloud fallback details, host thread allocation limits, and process-level memory footprint segments.
2. **[tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md) [MODIFY]**: Marked Phase 12 research constraints task as completed.

---

## 2. Locked-Down Specifications & Parameters

### 2.1 JNI Linkage bindings
- Dynamic Link Library (`LlmInferenceJni.dll`) dynamic loading mechanism specified.
- Java native methods mapped directly to native dynamic destructors, protecting native memory leaks.

### 2.2 DirectML adapter and GPU Delegate
- DirectX 12 D3D adapter enumeration via `IDXGIFactory4` defined.
- Shader cache caching path established to target optimal start times.

### 2.3 Cloud API priority fallbacks
- Integration with Gemini API client defined.
- Secure configuration settings locked via Windows Data Protection API (DPAPI).
- Catch-all fallback path defined: network disconnections or authentication failures within the Cloud engine automatically redirect execution context to the Local CPU engine.

### 2.4 Host CPU multi-threading
- Thread configuration defined: `num_threads = min(4, max(1, logical_cores / 2))` to prevent heat degradation.

### 2.5 Process memory footprint ceiling
- Standard limit constraints defined: Process RSS bounded between 3.38 GB (CPU Mode) and 3.68 GB (GPU Mode).

---

## 3. Verification & Critique Outcomes

1. **Self-Critique Outcome**: Added a dynamic network connection fail-safe rule. If the Priority 2 Cloud API fails due to network disconnection or invalid credentials, the system auto-routes the query back to the local CPU engine.
2. **Standard Requirements check**: Confirmed zero en/em-dashes exist in active specification updates and verified all file pathways are correct.
