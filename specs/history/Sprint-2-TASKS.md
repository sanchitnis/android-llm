# Project Specification Task Board

This board tracks the progress of the phase-by-phase loop-engineering workflow for the Admission Counselor AI specification.

## Phase Overview

| Phase | Description | Status |
| :--- | :--- | :--- |
| **Phase 0** | Repo Scaffolding and Spec Folder Setup | COMPLETED |
| **Phase 1** | Research Lock (LiteRT-LM & E2B parameters) | COMPLETED |
| **Phase 2** | Architecture Draft (Single-App & Threading) | COMPLETED |
| **Phase 3** | Domain Context and RAG Schema Draft | COMPLETED |
| **Phase 4** | Model Runtime and Resource Policy Draft | COMPLETED |
| **Phase 5** | App UI and ViewModel Design Draft | COMPLETED |
| **Phase 6** | Security, Data Privacy, and Sandboxing | COMPLETED |
| **Phase 7** | Build and Asset Packaging Plan (2.58 GB Model) | COMPLETED |
| **Phase 8** | Test and Verification Plan | COMPLETED |
| **Phase 9** | Documentation Consolidation | COMPLETED |
| **Phase 10** | Critique and Consistency Loop | COMPLETED |
| **Phase 11** | Final Verification Gate | COMPLETED |
| **Sprint 1** | Project Scaffold & Asset Delivery | COMPLETED |
| **Sprint 2** | Counselor Dataset Generation & Verification | ACTIVE |
| **Sprint 3** | On-Device RAG & Context Management | PENDING |
| **Sprint 4** | LiteRT-LM Engine & Concurrency | PENDING |
| **Sprint 5** | UI/UX & End-to-End Chat Flow | PENDING |
| **Sprint 6** | Security, Profiling, & Optimization | PENDING |

---

## Active Phase Tasks: Sprint 2

- [ ] Fetch and scan `reva.edu.in` for admission courses, fees, and deadlines <!-- id: s2_01 -->
- [ ] Parse extracted data into structured JSON format <!-- id: s2_02 -->
- [ ] Run anti-hallucination verification scripts against source HTML <!-- id: s2_03 -->
- [ ] Convert verified JSON into final SQLite `admission.db` format <!-- id: s2_04 -->
- [ ] Perform Sprint 2 Demo and await approval <!-- id: s2_05 -->
