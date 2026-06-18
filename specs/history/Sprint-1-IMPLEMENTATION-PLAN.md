# Implementation Sprint Plan

This document outlines the loop-engineering workflow's extension into the active development and implementation phase of the Android Admission Counselor AI.

## Workflow Transition
The project transitions from a 12-phase specification cycle to a 6-sprint implementation cycle. Each sprint is treated as an atomic loop iteration and concludes with a functional, testable slice of the application that must be demonstrated against strict pass/fail criteria.

### Artifact and Metric Archiving
At the conclusion of each implementation sprint, all telemetry, test reports, build logs, source code diffs, and updated metrics in `LOOP-LOG.md` must be actively archived into the `specs/history/` directory (e.g., `specs/history/Sprint-1-LOOP-LOG.md`). This ensures the loop engineering methodology's outermost loop (Research & Adaptation) continues to track actual implementation efforts, build processes, and performance data just as it did during the specification phases.

---

## Sprints

### Sprint 1: Project Scaffold & Asset Delivery
*   **Objective**: Initialize the Android project, establish dependency injection (Hilt/Koin), and implement the Play Asset Delivery (PAD) scaffolding for the model.
*   **Testing Requirements**: 
    *   `UT-DB-001`: Mock database copy verification.
    *   `IT-PAD-001`, `IT-PAD-002`: PAD download state tracking against mock asset packs.
*   **Demo**: App installs and launches to an initialization screen, effectively triggering a mock download progress bar that completes seamlessly.

### Sprint 2: Counselor Dataset Generation & Verification
*   **Objective**: Generate the definitive counselor dataset. Scan `reva.edu.in` and extract exact course details, tuition fees, application deadlines, and admission eligibility. Format this explicitly into the SQLite `admission.db` schema.
*   **Testing Requirements**: 
    *   **Anti-Hallucination Gate**: Automated validation scripts cross-referencing extracted JSON arrays directly against source HTML to prevent hallucinated fees or dates.
    *   Schema integrity and query logic checks.
*   **Demo**: Using an offline local raw SQL / CLI query interface to demonstrate precise, hallucination-free retrieval for sample student questions.

### Sprint 3: On-Device RAG & Context Management
*   **Objective**: Build the `RAGRetrievalManager` leveraging SQLite FTS5 logic. Create the context builder that injects matched context chunks into the model prompt.
*   **Testing Requirements**: 
    *   `UT-DB-002`: Query relevancy sorting verification.
    *   Constraint checks ensuring context injection never exceeds the 4096 context token budget limit.
*   **Demo**: A barebones Debug UI where querying "When is the BTech deadline?" prints the exact formatted context block that the LLM will see.

### Sprint 4: LiteRT-LM Engine & Concurrency
*   **Objective**: Integrate the `gemma-4-E2B-it` model binary with LiteRT-LM. Implement the background `LlmThread`, apply `Mutex` single-session locks, and handle automatic model unloads during idle/background states.
*   **Testing Requirements**: 
    *   `UT-MTX-001`, `UT-MTX-002`: Verifying the Mutex blocks multiple overlapping calls.
    *   `MP-LIM-001`, `MP-LIM-002`: Android Memory Profiler verifications for RAM cleanup.
*   **Demo**: A hardcoded prompt triggers inference via the local model; the text stream must print to the Logcat/UI without blocking the Android main thread.

### Sprint 5: UI/UX & End-to-End Chat Flow
*   **Objective**: Construct the final Jetpack Compose views following REVA University guidelines (Glacial Indifference font, `#f7a35b` primary branding). Connect `CounselorViewModel` to the RAG and LLM components.
*   **Testing Requirements**: 
    *   `UT-VM-001` through `UT-VM-003`: ViewModel state transition tracking.
    *   `UI-BRD-001` through `UI-BRD-004`: Automated Compose UI color and typography assertions.
*   **Demo**: End-to-end conversation flow in the polished UI. User asks a question -> App retrieves context -> Model streams answer.

### Sprint 6: Security, Profiling, & Optimization
*   **Objective**: Configure Android File-Based Encryption (FBE), build the GPU-to-CPU thermal fallback triggers, and profile end-to-end NFRs.
*   **Testing Requirements**: 
    *   `PB-THR-001`, `PB-THR-002`: Sustained load tests monitoring for thermal degradation and verifying 15 tokens/second TTFT.
    *   End-to-End verification checks validating the local sandbox prevents context leaking.
*   **Demo**: Application runs under sustained conversation load for 3+ minutes, handles thermal thresholds efficiently without crashing, and respects the 2.0 GB RAM constraint.
