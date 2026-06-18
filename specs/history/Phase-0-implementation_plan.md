# Spec Scaffolding & Phase Loop Setup

Establish the spec repository structure and the loop-engineering workflow in Antigravity. This project defines the full specification, architecture, and build plan for a standalone Android Admission Counselor App integrating LiteRT-LM and running `gemma-4-E2B-it` locally.

## User Review Required

> [!IMPORTANT]
> The workflow dictates that we will pause and **wait for human input/approval after completing every phase** before starting the next one.
>
> We will start with **Phase 0 (Repo Scaffolding and Spec Folder Setup)**. Once this phase is completed, verified, and approved by you, we will wait for your signal to begin Phase 1.

## Proposed Changes

### Configuration Files

#### [NEW] [AGENTS.md](file:///d:/Github/android-llm/AGENTS.md)
* Create `AGENTS.md` to define the mission, quality guidelines, and current target for the repository.

### Spec Scaffolding

#### [NEW] [specs/README.md](file:///d:/Github/android-llm/specs/README.md)
* A high-level introduction to the specification and the phase loop.

#### [NEW] [specs/LOOP-RULES.md](file:///d:/Github/android-llm/specs/LOOP-RULES.md)
* Explicit instructions for the loop-engineering workflow, including role simulation and the checklist for advancing phases.

#### [NEW] [specs/ISSUES.md](file:///d:/Github/android-llm/specs/ISSUES.md)
* The issue tracker for capturing unresolved architectural or design conflicts.

#### [NEW] [specs/tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* The task board tracking progress of specification phases.

## Verification Plan

### Manual Verification
1. Verify all required files for Phase 0 are successfully created under `specs/` and in the root.
2. Confirm the phase loop instructions in [LOOP-RULES.md](file:///d:/Github/android-llm/specs/LOOP-RULES.md) correctly align with the master prompt.
3. Wait for the user to review the files and give approval to proceed to Phase 1.
