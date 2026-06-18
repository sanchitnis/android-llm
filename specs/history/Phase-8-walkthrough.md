# Walkthrough: Phase 8 - Test & Verification Plan

We have successfully drafted the Test & Verification Plan specification and updated our progress metrics.

## Changes Completed

### Specifications
1. **[NEW] [TEST-PLAN.md](file:///d:/Github/android-llm/specs/TEST-PLAN.md)**:
   - Detailed automated testing strategy using JUnit, Espresso, Robolectric, and Jetpack Microbenchmarks.
   - Defined unit test cases for ViewModels, database copying/decryption with SQLCipher, and engine mutual exclusion locks.
   - Established integration test cases for Play Asset Delivery (PAD) progress handling and end-to-end local RAG inference flows.
   - Mapped performance, resource usage, and thermal throttling benchmarks to the PRD budget metrics.
   - Drafted low-memory (LOMEM) profiling procedures with clear ADB command simulations for moderate, low, and critical pressure.
   - Documented on-device RAG retrieval accuracy, hallucination checks, and context window constraint budgets.
   - Incorporated UI branding compliance checks (REVA colors `#f7a35b` / `#4a4c55`, Plus Jakarta Sans / Glacial Indifference fonts).
2. **[MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)**:
   - Updated Phase 7 status to `COMPLETED`.
   - Updated Phase 8 status to `IN PROGRESS` and checked off all tasks except the final gate review.

---

## Verification and Critique Passes

### Critique Pass Results
- **Prerequisites check**: No m-dashes (`—`) are used anywhere in the newly drafted `TEST-PLAN.md`.
- **Simplification**: Test plans use clear, direct sentences, avoiding unnecessary industry buzzwords.
- **Structured decisions**: Detailed testing targets, conditions, and expectations are organized into readable tables.
- **Hardware validation check**: Verified that the performance benchmarks correspond to the PRD latencies (TTFT < 0.5s GPU / < 1.5s CPU) and memory restrictions (2.0 GB ceiling).

### Verification Pass Results
- **Cross-file consistency**:
  - `TEST-PLAN.md` is fully consistent with the background threading model, Mutex locks, and single active session policy in `ARCHITECTURE.md`.
  - The dynamic delegates (ML Drift GPU / XNNPACK CPU), thermal states (Normal, Warm, Throttled), and LOMEM signals (MODERATE, LOW, CRITICAL) align with the runtime configurations defined in `MODEL-RUNTIME-POLICY.md`.
  - The SQLCipher decryption and database copy checks correspond with the data layout in `SECURITY-AND-PRIVACY.md` and context parameters in `COUNSELOR-CONTEXT-SCHEMAS.md`.
  - All resource limits match `PRD.md`.
- **Traceability**: All non-functional requirements (TTFT, memory, thermal scaling) have corresponding, measurable test cases.

---

## Telemetry Log Preparation

Before proceeding to Phase 9, the telemetry metrics for Phase 8 have been estimated and are ready to be written to `specs/LOOP-LOG.md`.

- **Active Specs Size**:
  - `TEST-PLAN.md`: ~6,000 bytes
  - Total Active Specs Size: ~59,700 bytes
- **Defects Detected**: 0 (Critique pass confirmed direct compliance with all rules).
- **User Iterations**: 0 (Direct transition).
- **Critique Efficiency Index (CEI)**: 1.0 (Direct compliance).
