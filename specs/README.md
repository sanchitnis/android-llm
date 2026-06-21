# Android Admission Counselor AI App Specification

This directory contains the implementation-ready specification, design guidelines, model policies, and verification protocols for the standalone **Android Admission Counselor App**.

## Objective
The goal of this specification is to define the architecture, data schemas, threading mechanisms, and build plans to run local LLM inference using **LiteRT-LM** with the **gemma-4-E2B-it** model (2.58 GB) on-device.

## Project Scope
- **App Boundary**: Single Android application wrapper containing both the inference runtime and UI. No cross-app IPC or external API server dependencies.
- **Model Target**: Exclusively the `gemma-4-E2B-it` model.
- **On-Device Data**: Context injection / structured prompting to serve as the Counselor Knowledge Base (courses, fees, deadlines).
- **Concurrency**: Dedicated background thread inference (using Kotlin Coroutines or Executor service) ensuring the UI remains highly responsive.
- **Resource Constraints**: Strict single active inference session policy to prevent memory exhaustion and thermal throttling.

## Specification Index

| Document | Description |
| :--- | :--- |
| [README.md](file:///d:/Github/android-llm/specs/README.md) | This directory overview and index. |
| [PRD.md](file:///d:/Github/android-llm/specs/PRD.md) | Product Requirements Document. |
| [ARCHITECTURE.md](file:///d:/Github/android-llm/specs/ARCHITECTURE.md) | Standalone app and background threading architecture. |
| [COUNSELOR-CONTEXT-SCHEMAS.md](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md) | Schema definitions for admission courses, fees, and retrieval. |
| [MODEL-RUNTIME-POLICY.md](file:///d:/Github/android-llm/specs/MODEL-RUNTIME-POLICY.md) | Runtime guidelines, memory allocation, and thermal/RAM management. |
| [APP-DESIGN.md](file:///d:/Github/android-llm/specs/APP-DESIGN.md) | ViewModels, UI flows, and UI-to-engine lifecycle. |
| [SECURITY-AND-PRIVACY.md](file:///d:/Github/android-llm/specs/SECURITY-AND-PRIVACY.md) | Data privacy, storage sandboxing, and compliance framework. |
| [BUILD-GUIDE.md](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md) | Asset packaging guide for the 2.58 GB model file. |
| [TEST-PLAN.md](file:///d:/Github/android-llm/specs/TEST-PLAN.md) | Validation, profiling, and benchmarking suite. |
| [IMPLEMENTATION-PLAN.md](file:///d:/Github/android-llm/specs/IMPLEMENTATION-PLAN.md) | Master sprint roadmap and implementation tracker. |
| [PWA-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/PWA-ARCHITECTURE.md) | Progressive Web App specification (WebGPU, SQLite Wasm, OPFS, Web Workers). |
| [WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md) | Windows Desktop App architecture (Compose Multiplatform, LiteRT CPU/GPU, shared asset weights, and sandboxing). |
| [ISSUES.md](file:///d:/Github/android-llm/specs/ISSUES.md) | List of open issues, trade-offs, and design gaps. |
| [TRACEABILITY.md](file:///d:/Github/android-llm/specs/TRACEABILITY.md) | Requirement-to-test and requirement-to-file traceability matrix. |
| [LOOP-RULES.md](file:///d:/Github/android-llm/specs/LOOP-RULES.md) | Loop-engineering workflow rules. |
| [LOOP-LOG.md](file:///d:/Github/android-llm/specs/LOOP-LOG.md) | Loop-engineering metrics log (tokens, elapsed time, iterations). |
| [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md) | Atomic phase tasks board. |
| [history/](file:///d:/Github/android-llm/specs/history/) | Evolutionary logs and archived plans, walkthroughs, tasks, and specs from previous phases. |

