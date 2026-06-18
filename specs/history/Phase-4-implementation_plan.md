# Phase 3: Domain Context & RAG Schema Plan

Define the on-device data storage schema, RAG index tables, and context injection prompt templates in the Counselor Context Schemas document.

## User Review Required

> [!IMPORTANT]
> This phase determines how college programs, fees, eligibility criteria, and deadlines are structured locally.
>
> We will draft [COUNSELOR-CONTEXT-SCHEMAS.md](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md) specifying SQLite database tables, retrieval logic, and prompt schemas.

## Proposed Changes

### Spec Scaffolding

#### [NEW] [COUNSELOR-CONTEXT-SCHEMAS.md](file:///d:/Github/android-llm/specs/COUNSELOR-CONTEXT-SCHEMAS.md)
* Create the Counselor Context Schemas document detailing:
  * Entity schemas (Program, TuitionFee, EligibilityRule, AdmissionDeadline).
  * SQLite table schemas and index mappings to speed up context lookups.
  * Retrieval workflow: User query -> Full-Text-Search (FTS5) SQLite search -> Context assembly.
  * System prompt template showing how retrieved data is injected into Gemma 4's context block.

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* Update progress status of Phase 3 and define tasks for Domain Context.

## Verification Plan

### Manual Verification
1. Verify `specs/COUNSELOR-CONTEXT-SCHEMAS.md` follows quality rules (no m-dashes, simple language, tables for schemas).
2. Ensure RAG retrieval limits conform to the 2048 token window budget specified in the PRD.
3. Pause and request user approval for Phase 3 Domain Context and RAG Schema.

