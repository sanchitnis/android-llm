# Antigravity Code Repo Instructions - Admission Counselor AI

## Mission
Produce an implementation-ready specification for a standalone Android Admission Counselor App running gemma-4-E2B-it locally.
Do not implement the app yet.

## Mandatory workflow
1. Work spec-first under `/specs`.
2. Update `specs/tasks/TASKS.md` before and after substantial work.
3. Run a critique pass after each artifact update.
4. Run a verification pass after each critique pass.
5. Track blockers in `specs/ISSUES.md`.
6. Do not advance phases until the current phase gate passes.
7. Archive all phase plans, walkthroughs, tasks, and modified specs under `specs/history/` at the end of each phase for loop-engineering log analysis.

## Quality rules
- Be precise.
- Use clear dates.
- Use simple language.
- Do not use m-dashes.
- Prefer tables for structured decisions.
- Focus strictly on local inference without external API or IPC overhead.

## Current target
Define:
- Standalone app architecture & background threading for LiteRT-LM.
- On-device admission data schemas/context injection.
- Storage and RAM validation for the 2.58 GB gemma-4-E2B-it model.
- App UI-to-Engine interaction lifecycle.

## Final output standard
All required docs under `/specs` must be internally consistent, implementation-ready, and pass the final verification gate.
