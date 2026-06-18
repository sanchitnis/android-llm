# Requirements Traceability Matrix - Admission Counselor AI

Last Modified: June 18, 2026

This document establishes the end-to-end traceability of all functional, non-functional, and security requirements defined in the Product Requirements Document (PRD) to their specific implementation designs and verification tests.

---

## 1. Specification Document Index

The following table indexes all active specification documents within the `/specs` directory, detailing their scope and current verification status.

| Document Name | File Path | Primary Focus | Verification Status |
| :--- | :--- | :--- | :--- |
| **PRD** | [PRD.md](file:///d:/Github/android-llm/specs/PRD.md) | High-level requirements, user stories, hardware baseline, and performance budgets. | VERIFIED |
| **Architecture** | [ARCHITECTURE.md](file:///d:/Github/android-llm/specs/ARCHITECTURE.md) | Single-app containment model, thread containment, background coroutine dispatchers, and Mutex locking. | VERIFIED |
| **Context Schemas** | [COUNSELOR-CONTEXT-SCHEMAS.md](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md) | Local database schemas, RAG text chunking, and 2048-token context window budget allocation. | VERIFIED |
| **Model Runtime Policy** | [MODEL-RUNTIME-POLICY.md](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md) | Hardware delegate rules, speculative decoding, low-memory (LOMEM) actions, and thermal throttling mitigations. | VERIFIED |
| **App Design** | [APP-DESIGN.md](file:///d:/Github/android-llm/specs/APP-DESIGN.md) | Jetpack Compose UI layout, REVA University color/typography codes, and ViewModel state-machine flows. | VERIFIED |
| **Security & Privacy** | [SECURITY-AND-PRIVACY.md](file:///d:/Github/android-llm/specs/SECURITY-AND-PRIVACY.md) | Sandboxed local storage paths, SQLCipher decryption, and volatile transcript memory policies. | VERIFIED |
| **Build Guide** | [BUILD-GUIDE.md](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md) | Gradle module linking, Google Play Asset Delivery configuration, and SQLite asset copy logic. | VERIFIED |
| **Test Plan** | [TEST-PLAN.md](file:///d:/Github/android-llm/specs/TEST-PLAN.md) | Automated unit tests, integration test suites, low-memory profiling simulations, and performance benchmarks. | VERIFIED |

---

## 2. Functional Requirements Traceability Matrix

This matrix maps each functional requirement from the PRD to its architectural design components, codebase specifications, and verification test cases.

| PRD Section | Requirement Description | Design Specification Location | Verification Test Case ID |
| :--- | :--- | :--- | :--- |
| **Section 5.1** | Conversational chat interface displaying incremental streamed tokens. | - [APP-DESIGN.md Section 1 (UI Wireframes)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L7-L50)<br>- [ARCHITECTURE.md Section 3.1 (Flow Emission)](file:///d:/Github/android-llm/specs/ARCHITECTURE.md#L60-L107) | - **UT-VM-001** (Lazy load stream transition)<br>- **IT-RAG-001** (End-to-end token flow) |
| **Section 5.1** | Thumbs up/down feedback mechanisms for responses. | - [APP-DESIGN.md Section 1.2 (Feedback UI)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L35-L42)<br>- [COUNSELOR-CONTEXT-SCHEMAS.md Section 3 (Interaction Schema)](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md#L110-L135) | - **UT-DB-002** (Verify feedback logging query) |
| **Section 5.2** | On-device RAG database containing admission, fee, and course details. | - [COUNSELOR-CONTEXT-SCHEMAS.md Section 1 (Admission Schema)](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md#L10-L60)<br>- [BUILD-GUIDE.md Section 3 (Database Assets)](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md#L60-L80) | - **UT-DB-001** (Decryption and database access)<br>- **UT-DB-003** (Skip copy logic on warm start) |
| **Section 5.2** | Context retrieval matching and injection into model prompt. | - [COUNSELOR-CONTEXT-SCHEMAS.md Section 2 (Context Budgeting)](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md#L62-L105)<br>- [ARCHITECTURE.md Section 1 (RAG Manager Diagram)](file:///d:/Github/android-llm/specs/ARCHITECTURE.md#L11-L35) | - **IT-RAG-001** (Context retrieval injection verification)<br>- **Section 6.1 (Accuracy & Injection Tests)** |
| **Section 5.3** | Lazy model loading upon active session initiation. | - [ARCHITECTURE.md Section 5 (Lifecycle State Diagram)](file:///d:/Github/android-llm/specs/ARCHITECTURE.md#L126-L135)<br>- [ARCHITECTURE.md Section 3.1 (`getOrInitializeEngine`)](file:///d:/Github/android-llm/specs/ARCHITECTURE.md#L94-L106) | - **UT-VM-001** (Lazy load UI transition)<br>- **MP-LIM-001** (First load verification) |
| **Section 5.3** | 5-Minute inactivity model unloading to free RAM. | - [ARCHITECTURE.md Section 5.1 (Idle Timeout Unload)](file:///d:/Github/android-llm/specs/ARCHITECTURE.md#L137-L141)<br>- [MODEL-RUNTIME-POLICY.md Section 5 (Idle Lifetime)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L84-L88) | - **MP-LIM-001** (Inactivity timeout profiling) |
| **Section 5.3** | Background activity release (ON_STOP) memory teardown. | - [ARCHITECTURE.md Section 5.2 (Background Lifecycles)](file:///d:/Github/android-llm/specs/ARCHITECTURE.md#L143-L147)<br>- [MODEL-RUNTIME-POLICY.md Section 5 (Idle Lifetime)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L84-L88) | - **MP-LIM-002** (App backgrounding memory release) |

---

## 3. Non-Functional Requirements (NFR) Traceability Matrix

This matrix maps non-functional constraints (budgets, accelerators, and limits) defined in the PRD to the design rules and benchmarks configured to test them.

| PRD Section | NFR Parameter | Target Budget | Design Mitigation Specification | Verification Test ID |
| :--- | :--- | :--- | :--- | :--- |
| **Section 6.1** | GPU Latency (TTFT) | Under 0.5 seconds | - [MODEL-RUNTIME-POLICY.md Section 1.1 (ML Drift config)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L21-L28) | - **Section 4.1 Latency Benchmarks (TTFT GPU)** |
| **Section 6.1** | CPU Latency (TTFT) | Under 1.5 seconds | - [MODEL-RUNTIME-POLICY.md Section 1.1 (XNNPACK config)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L21-L28) | - **Section 4.1 Latency Benchmarks (TTFT CPU)** |
| **Section 6.1** | GPU Throughput | 15 tokens/second or higher | - [MODEL-RUNTIME-POLICY.md Section 2 (Speculative Decoding)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L30-L42) | - **Section 4.1 Latency Benchmarks (Throughput GPU)** |
| **Section 6.1** | CPU Throughput | 5 tokens/second or higher | - [MODEL-RUNTIME-POLICY.md Section 1.1 (XNNPACK Threading)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L21-L28) | - **Section 4.1 Latency Benchmarks (Throughput CPU)** |
| **Section 6.2** | Peak RAM Allocation | Under 2.0 GB | - [COUNSELOR-CONTEXT-SCHEMAS.md Section 2.1 (Context Limits)](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md#L70-L85)<br>- [MODEL-RUNTIME-POLICY.md Section 3 (LOMEM Strategy)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L45-L68) | - **PB-RES-001** (Peak RAM measurement)<br>- **MP-LIM-003** (LOMEM trim profiling) |
| **Section 6.2** | App Package Size (APK) | Under 150 MB (model excluded) | - [BUILD-GUIDE.md Section 1 (PAD config)](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md#L9-L32) | - **Section 1.1 Integration test size check** |
| **Section 6.2** | Thermal Degradation | Under 20% over 3 minutes | - [MODEL-RUNTIME-POLICY.md Section 4 (Thermal States)](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md#L70-L82) | - **PB-THR-001** (Thermal throughput decay check)<br>- **PB-THR-002** (Pause delay check)<br>- **PB-THR-003** (Delegate fallback check) |

---

## 4. Security & Privacy Requirements Traceability Matrix

This matrix maps security and sandboxing constraints from the privacy specification to implementation architectures and test controls.

| Security Directive | Target Policy | Design Mitigation Specification | Verification Test ID |
| :--- | :--- | :--- | :--- |
| **Zero Network Permissions** | Eliminate all network risk. | - [SECURITY-AND-PRIVACY.md Section 1 (Manifest topology)](file:///d:/Github/android-llm/specs/SECURITY-AND-PRIVACY.md#L7-L22)<br>- [BUILD-GUIDE.md Section 1.1 (build.gradle configs)](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md#L15-L25) | - **Section 1.1 Verification check of final AndroidManifest.xml** |
| **Storage Sandboxing** | Prevent model/db access from other apps. | - [SECURITY-AND-PRIVACY.md Section 4 (Sandboxed Paths)](file:///d:/Github/android-llm/specs/SECURITY-AND-PRIVACY.md#L62-L80) | - **UT-DB-001** (Confirm copy to isolated app storage) |
| **Database Encryption** | Protect offline intellectual property. | - [SECURITY-AND-PRIVACY.md Section 2 (SQLCipher Encryption)](file:///d:/Github/android-llm/specs/SECURITY-AND-PRIVACY.md#L24-L44) | - **UT-DB-001** (Verify non-readable plaintext format via DB tools) |
| **Volatile Transcripts** | Protect student privacy. | - [SECURITY-AND-PRIVACY.md Section 3 (Memory Sandbox)](file:///d:/Github/android-llm/specs/SECURITY-AND-PRIVACY.md#L46-L60) | - **UT-VM-001** (Verify volatile in-memory StateFlow clearance)<br>- **MP-LIM-002** (Verify memory release on background) |

---

## 5. UI Theme & Brand Guidelines Traceability Matrix

This matrix maps the REVA University brand constraints to UI visual assets and Compose testing rules.

| Brand Parameter | Guidelines Target | Design Specification Location | Compose UI Test ID |
| :--- | :--- | :--- | :--- |
| **Primary Theme Color** | REVA Orange `#f7a35b` | - [APP-DESIGN.md Section 3 (Theme Colors)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L90-L115) | - **UI-BRD-001** (Hex code theme mapping check) |
| **Secondary Theme Color** | REVA Grey `#4a4c55` | - [APP-DESIGN.md Section 3 (Theme Colors)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L90-L115) | - **UI-BRD-001** (Hex code theme mapping check) |
| **Typography (Headers)** | Plus Jakarta Sans | - [APP-DESIGN.md Section 4 (Typography)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L117-L135) | - **UI-BRD-002** (Compose font family validation) |
| **Typography (Body)** | Glacial Indifference | - [APP-DESIGN.md Section 4 (Typography)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L117-L135) | - **UI-BRD-002** (Compose font family validation) |
| **Toolbar Branding** | Logo spacing with 16dp margins | - [APP-DESIGN.md Section 1.1 (Toolbar Layout)](file:///d:/Github/android-llm/specs/APP-DESIGN.md#L15-L25) | - **UI-BRD-003** (Compose element constraint layout check) |
