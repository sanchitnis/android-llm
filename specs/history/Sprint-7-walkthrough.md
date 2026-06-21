# Walkthrough: Sprint 7 (Brochure Data Ingestion)

This walkthrough documents the completion of the ingestion of the REVA University Brochure 2026-27 Data Extract into the local SQLite database and its distribution to the application assets.

**Document Date**: June 19, 2026

---

## 1. Summary of Changes

We expanded the offline-first admission dataset to cover 100% of the information provided in the brochure extract. The following files were created or modified:

1. **[populate_reva_programs.py](file:///d:/Github/android-llm/scripts/data_pipeline/populate_reva_programs.py) [MODIFY]**: Expanded the dataset from 58 to 75 entries, incorporating specific Ph.D. areas, detailed minor/honours programmes, QS Southern Asia Rank 292, and the THE Impact Rankings 2025 details.
2. **[admission.db](file:///d:/Github/android-llm/app/src/main/assets/admission.db) [MODIFY]**: Rebuilt the SQLite database and copied it to the app's assets folder as the new database template seed.
3. **[TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md) [MODIFY]**: Added Sprint 7 tasks to track this ingestion work, and marked all tasks as completed after verification.
4. **[test_db.py](file:///C:/Users/sanjay.chitnis/.gemini/antigravity-ide/brain/0d645cf8-bf96-4d26-afe1-a6157f4cf62e/scratch/test_db.py) [NEW]**: Created a python scratch test script to verify FTS queries on the database.

---

## 2. Ingested Data Scope

We mapped general university information, rankings, facilities, and placements as course-like entries in the SQLite database to allow natural language queries to retrieve this context via the on-device FTS index. 

The ingested data includes:
- **General Info**: KCET Code E232, COMEDK Code E164, contact details, and location.
- **Rankings**: NAAC A+, NBA CSE/ECE/Mechanical, NIRF, QS World Asia 1001-1100, Southern Asia 292, Times Higher Education 1501+, THE Impact Rankings 2025 (SDG 3, 4, 16, 17), and QS I-GAUGE Diamond Rating.
- **Metrics**: 16000+ students, 40000+ alumni, 700+ Ph.D. scholars, 1000+ patents, and 90+ startups.
- **Infrastructure**: REVA Research Centre, REVA NEST, AR/VR Labs, Fab Labs, hostels, sports, and student life.
- **Placements**: 450+ partners, 3533+ job offers, highest package of INR 56 LPA, highest monthly stipend of INR 1.16 Lakh, and top hiring firms.
- **Academic Programs**: Detailed course listings across all faculties, special minor/honours streams, collaborative minors with IITs, and PhD offerings for every department.

---

## 3. Verification & Validation

To guarantee accuracy and compatibility:
1. **Anti-Hallucination Gate**: Ran `02_verify_data.py` to compare all numbers and dates between the raw JSON records and the source HTML. The checks passed with a 100% match.
2. **SQLite Database Compilation**: Successfully executed `03_build_sqlite_db.py` to compile the records into `data/admission.db` with FTS5 virtual tables enabled.
3. **App Asset Seeding**: Copied the database to the app's asset folder, updating `app/src/main/assets/admission.db`.
4. **FTS Search Retrieval Test**: Executed the query sanitization and matching logic in the python test script to verify that:
   - Special characters such as dots in "Ph.D." are sanitized.
   - Stopwords are stripped out.
   - Token search terms are quoted correctly to prevent SQL/FTS operator injection.
   - The test script correctly matched query terms like "COMEDK", "Accenture", "NAAC", "Ph.D. in Architecture", and "General Minor Programmes".

All tests were completed successfully.
