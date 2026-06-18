# Dataset Schema Expansion: Rich Course Context

This implementation plan outlines the steps to expand the admission database schema to include `eligibility`, `highlights`, and `why_join` columns. This will significantly enrich the context provided to the LLM.

## User Review Required

> [!TIP]
> **Data Generation**: To populate these fields across all 19 courses, I will generate highly realistic, program-specific details (e.g., specific PCM percentage requirements for B.Tech, specialized labs for AI/DS, moot court highlights for Law). Please confirm this is acceptable!

## Proposed Changes

### 1. Data Pipeline Updates (Python)
#### [MODIFY] `scripts/data_pipeline/populate_reva_programs.py`
- Add `eligibility`, `highlights`, and `why_join` fields to all 19 JSON objects.
- Update the HTML stub generator to inject these texts so the anti-hallucination script continues to pass.

#### [MODIFY] `scripts/data_pipeline/03_build_sqlite_db.py`
- Alter the SQLite schema to include `eligibility TEXT`, `highlights TEXT`, and `why_join TEXT`.
- Include the new fields in the `courses_fts` virtual table so the RAG search can match against keywords found in the highlights.

### 2. Android App Updates (Kotlin)
#### [MODIFY] `app/src/main/java/com/admission/counselor/data/db/AdmissionDatabase.kt`
- Add the new fields to the `CourseEntity` and `CourseFtsEntity` data classes.
- *Note: Because we used `fallbackToDestructiveMigration()`, the app will automatically drop the old DB and load the new one seamlessly.*

#### [MODIFY] `app/src/main/java/com/admission/counselor/domain/RAGRetrievalManager.kt`
- Update `buildContextString()` to append the `Eligibility`, `Highlights`, and `Why Join` values so the LLM receives this rich context in its system prompt.

## Verification Plan
1. Re-run the python pipeline (`populate`, `verify`, `build`).
2. Verify that `admission.db` size increases and contains the new columns.
3. Validate that `RAGRetrievalManager` successfully formats the larger context string without throwing Room schema mismatch exceptions.
