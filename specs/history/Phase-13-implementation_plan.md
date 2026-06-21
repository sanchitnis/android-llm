# Phase 13: Windows App Architecture Implementation Plan

Draft the detailed Windows Multiplatform code-sharing architecture, database seeding strategy, and thread containment model in [WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md).

## User Review Required

> [!IMPORTANT]
> - We will detail the shared business layer versus platform-specific implementations.
> - The database replication logic will check for database updates and clone the template on Windows launch.
> - Coroutine-based threading model on Windows desktop JRE will be specified.

## Proposed Changes

### Specifications

#### [MODIFY] [WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md)
Add the following detailed design sections:
- **Section 1.1: Multiplatform Code-Sharing Model**: Class directory mapping for shared `commonMain` logic (ViewModels, entities, RAG context builders) and target-specific `desktopMain`/`androidMain` bindings.
- **Section 2.3: Windows Database Seeding & Migration**: Precise file copy steps from JRE resources package to `%LOCALAPPDATA%\AdmissionCounselor\data\admission.db`, including file update checks and checksum verifications.
- **Section 3.2: Desktop Thread Dispatching and Concurrency Flow**: Detailed diagram mapping UI-to-Engine JNI thread execution context switches.

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
Mark Phase 13 tasks as completed.

---

## Verification Plan

### Manual Verification
- Verify architectural alignment with Phase 12 runtime constraints and existing Android security principles.
- Perform a consistency pass ensuring no informal dashes or placeholders exist in active spec documents.
