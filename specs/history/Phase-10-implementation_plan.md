# Phase 10: Critique & Consistency Loop

Perform a comprehensive cross-reference audit across all active specifications under `/specs` to ensure complete consistency of parameters, validation of all file links, adherence to the zero-m-dash rule, and a finalized issues register.

## User Review Required

> [!IMPORTANT]
> This phase performs a quality control sweep over the entire spec directory before the final verification gate.
>
> We will verify that:
> 1. All markdown links and line anchors are fully correct.
> 2. No forbidden characters (en-dashes or em-dashes) exist in active documents.
> 3. Key parameters (RAM ceilings, model name, token budget splits, brand HEX colors, and timeouts) are identical across all specs.

## Proposed Changes

### Auditing Specifications

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* Update progress status of Phase 10 and list active critique tasks.

#### [MODIFY] [ISSUES.md](file:///d:/Github/android-llm/specs/ISSUES.md)
* If any discrepancies are found during the audit, log them as open issues and resolve them. If none are found, confirm the tracker is clean.

## Verification Plan

### Manual Verification
1. Run automated search scripts or grep to find any instances of `—` (em-dash) or `–` (en-dash).
2. Manually test or scan all relative links to ensure they resolve to valid targets.
3. Check and list all core parameter constants across all documents to prove absolute consistency.
4. Pause and request user approval for Phase 10 Critique and Consistency Loop.
