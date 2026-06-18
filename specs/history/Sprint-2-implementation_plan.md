# Sprint 2: Counselor Dataset Generation & Verification

This implementation plan outlines the steps to build the definitive data pipeline for the Admission Counselor AI. It focuses on scraping `reva.edu.in`, structuring the data, and applying rigorous verification gates to ensure zero hallucinations.

## User Review Required

> [!WARNING]
> **Data Availability constraints**: REVA University does not publish a single, consolidated fee list online; fees often vary heavily based on student category (e.g., KCET vs. COMEDK vs. Management quota). 
> **Question**: Should we target a simplified "Base Tuition Fee" for our dataset, or should we program the LLM to explicitly state "Please contact admissions for exact fee breakdown" when fees aren't explicitly scraped?

> [!IMPORTANT]
> **Scraping Protections**: If the university website employs anti-bot measures (like Cloudflare), basic Python `requests` might fail. 
> **Question**: If automated scraping fails, is it acceptable to manually save the HTML pages to the repository for the Python scripts to parse locally?

## Proposed Changes

### 1. Data Scraping Scripts
#### [NEW] `scripts/data_pipeline/01_fetch_reva_data.py`
- A Python script utilizing `requests` and `BeautifulSoup` to target the specific school pages (e.g., School of Engineering, School of Management).
- Extracts raw course titles, program durations, basic eligibility criteria, and any explicitly stated fees.

### 2. Structured JSON Formatting
#### [NEW] `scripts/data_pipeline/data/raw_courses.json`
- The intermediate JSON format. The scraping script will dump the structured data here for inspection.

### 3. Anti-Hallucination Verification Gate
#### [NEW] `scripts/data_pipeline/02_verify_data.py`
- A strict validation script that reads `raw_courses.json` and asserts that **every single numerical value** (fees, dates, eligibility percentages) exists as an exact substring within the originally downloaded source HTML.
- **Fail Condition**: If the script detects a number or date in the JSON that was not found verbatim in the source HTML, the pipeline halts immediately. This prevents LLM or parser hallucinations from entering the final database.

### 4. Database Compilation
#### [NEW] `scripts/data_pipeline/03_build_sqlite_db.py`
- A Python script that utilizes the `sqlite3` library to convert the verified `raw_courses.json` into the `admission.db` SQLite file.
- Sets up the `FTS5` (Full-Text Search) tables required for the Android app's `RAGRetrievalManager`.

### 5. Android Asset Integration
#### [NEW] `app/src/main/assets/admission.db`
- The final, compiled SQLite database is copied into the Android project's `assets/` directory, ready to be provisioned on first launch.

## Verification Plan

### Automated Tests
- Run `python scripts/data_pipeline/02_verify_data.py` to ensure the anti-hallucination gate passes with a 100% confidence rating across all entries.

### Manual Verification
- We will execute sample SQL queries against the generated `admission.db` using the SQLite CLI (e.g., `SELECT * FROM courses WHERE name LIKE '%B.Tech%'`) to visually confirm data structure and integrity before deploying the asset to the app.
