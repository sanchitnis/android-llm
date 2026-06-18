# Phase 2: Architecture Draft Plan

Define the standalone app architecture, background threading patterns, and the single-session engine locks for local LLM inference in the Architecture document.

## User Review Required

> [!IMPORTANT]
> This phase establishes the core threading and session locking mechanisms for LiteRT-LM on Android.
>
> We will draft [ARCHITECTURE.md](file:///d:/Github/android-llm/specs/ARCHITECTURE.md) defining the Coroutine-based background containment model and the mutual exclusion engine lock.

## Proposed Changes

### Spec Scaffolding

#### [NEW] [ARCHITECTURE.md](file:///d:/Github/android-llm/specs/ARCHITECTURE.md)
* Create the Architecture document detailing:
  * Standalone single-app boundaries and block diagrams.
  * Threading structure: Main/UI thread vs. Background Dispatcher (Kotlin Coroutines `Dispatchers.Default` / single-threaded custom dispatcher).
  * Synchronization and locking model: Mutex lock to enforce the single active inference session.
  * Runtime engine lifecycle: Initialization, inference execution loop, and model resource releasing.

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* Update progress status of Phase 2 and define tasks for Architecture Draft.

## Verification Plan

### Manual Verification
1. Verify `specs/ARCHITECTURE.md` follows quality rules (no m-dashes, simple language, tables for design decisions).
2. Check that the design maps directly to the constraints (single-app boundary, non-blocking UI thread, single active inference session).
3. Pause and request user approval for Phase 2 Architecture Draft.

