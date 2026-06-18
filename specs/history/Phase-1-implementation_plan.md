# Phase 1: Research Lock Plan

Define and lock down the research parameters for LiteRT-LM, `gemma-4-E2B-it` model specifications, memory requirements, and performance characteristics in the Product Requirements Document (PRD).

## User Review Required

> [!IMPORTANT]
> This phase locks down the technical parameters that will govern all subsequent design documents (Architecture, Model Runtime Policy, App Design, etc.).
>
> We will draft [PRD.md](file:///d:/Github/android-llm/specs/PRD.md) using the verified constraints and researched mobile performance baselines.

## Proposed Changes

### Spec Scaffolding

#### [NEW] [PRD.md](file:///d:/Github/android-llm/specs/PRD.md)
* Create the Product Requirements Document (PRD) detailing:
  * Model specifications (gemma-4-E2B-it, 2.58 GB footprint).
  * Target hardware baseline (RAM, CPU, GPU requirements).
  * Core functional requirements (admission query handling, context injection RAG).
  * Non-functional requirements (latency, responsiveness, thermals).
  * Threading and session isolation constraints.

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* Update progress status of Phase 1 and define detailed tasks for Research Lock.

## Verification Plan

### Manual Verification
1. Verify `specs/PRD.md` conforms to the quality rules (no m-dashes, simple language, precise values, tables for decisions).
2. Confirm all constraints listed in [AGENTS.md](file:///d:/Github/android-llm/AGENTS.md) are incorporated.
3. Pause and request user approval for Phase 1 Research Lock.
