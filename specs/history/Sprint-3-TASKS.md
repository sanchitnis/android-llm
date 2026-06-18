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
| **Sprint 2** | Counselor Dataset Generation & Verification | COMPLETED |
| **Sprint 3** | On-Device RAG & Context Management | ACTIVE |
| **Sprint 4** | LiteRT-LM Engine & Concurrency | PENDING |
| **Sprint 5** | UI/UX & End-to-End Chat Flow | PENDING |
| **Sprint 6** | Security, Profiling, & Optimization | PENDING |

---

## Active Phase Tasks: Sprint 3

- [ ] Implement `RAGRetrievalManager` querying SQLite DB <!-- id: s3_01 -->
- [ ] Construct context injection prompts for the LLM <!-- id: s3_02 -->
- [ ] Set up ViewModel to orchestrate RAG pipeline <!-- id: s3_03 -->
- [ ] Write Integration Test `IT-RAG-001` <!-- id: s3_04 -->
- [ ] Perform Sprint 3 Demo and await approval <!-- id: s3_05 -->
