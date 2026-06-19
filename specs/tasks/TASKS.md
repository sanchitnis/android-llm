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
| **Sprint 3** | On-Device RAG & Context Management | COMPLETED |
| **Sprint 4** | LiteRT-LM Engine & Concurrency | COMPLETED |
| **Sprint 5** | UI/UX & End-to-End Chat Flow | COMPLETED |
| **Sprint 6** | Security, Profiling, & Optimization | COMPLETED |

---

## Active Phase Tasks: Sprint 6

- [x] Implement thermal state listener via `PowerManager.getThermalStatus()` <!-- id: s6_01 -->
- [x] Configure dynamic GPU-to-CPU fallback on high thermal thresholds <!-- id: s6_02 -->
- [x] Implement KV cache pruning on OS memory trim warnings <!-- id: s6_03 -->
- [x] Implement diagnostic log outputs for token latency (TTFT) and throughput <!-- id: s6_04 -->
- [x] Perform Sprint 6 Demo and await final approval <!-- id: s6_05 -->

---

## Future Backlog: Google Play Store Release Compliance

- [ ] Migrate model selection from raw file paths to Storage Access Framework (SAF) using `Intent.ACTION_OPEN_DOCUMENT` to comply with Google Play's Scoped Storage guidelines. <!-- id: gp_01 -->
- [ ] Implement system Uri persistence and stream copy to duplicate the selected weights file into `context.filesDir`. <!-- id: gp_02 -->
- [ ] Add post-copy user prompt with instructions to delete the duplicate download file manually, or request deletion permission via `DocumentsContract.deleteDocument()`. <!-- id: gp_03 -->
- [ ] Implement SHA-256 model checksum verification upon import to guarantee model integrity before loading it into LiteRT-LM. <!-- id: gp_04 -->
