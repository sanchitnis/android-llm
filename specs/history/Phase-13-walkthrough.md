# Walkthrough: Phase 13 (Windows App Architecture Draft)

This walkthrough documents the completion of **Phase 13: Windows App Architecture Draft** for the Admission Counselor AI.

**Document Date**: June 21, 2026

---

## 1. Summary of Changes

We finalized the code-sharing patterns, offline database seeding protocols, and JRE/native threading boundaries for the Windows Multiplatform extension. The following files were created or modified:

1. **[WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md) [MODIFY]**:
   - Added Section 1.1 detailing the shared ViewModels, RAG context builders, and platform-specific engine and database configurations inside `commonMain`, `androidMain`, and `desktopMain`.
   - Added Section 2.3 outlining the SQLite database dynamic seeding path, `PRAGMA user_version` checking, and backup copy operations.
   - Added Section 3.2 mapping the UI-to-Native context switches with an detailed Mermaid sequence diagram tracking token flow.
2. **[tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md) [MODIFY]**: Marked Phase 13 architecture design task as completed.

---

## 2. Windows Architecture Specifications

### 2.1 Multiplatform Sharing Layer
- Views, ViewModels, and prompt tokenization are kept in `commonMain`.
- The database uses Room Database on Android (`androidMain`) and SQLite JDBC on Windows desktop (`desktopMain`).
- Local inference is run via LiteRT dynamic bindings in `desktopMain`.

### 2.2 Database Dynamic Replication
- The database is copied from the app JAR resource folder on the first start.
- On updates, schema version comparison is performed using `PRAGMA user_version`. Higher versions trigger a safe overwrite, backing up the previous DB template file without affecting any local user configuration.

### 2.3 Context Switching Threading Pipeline
- The Mermaid flow charts token updates yielding: Native C++ -> JNI callback -> JVM Executor -> CounselorViewModel StateFlow accumulator -> Compose main rendering thread.

---

## 3. Verification & Critique Pass

1. **Self-Critique Verification**: Confirmed that overwriting the database on version updates does not lose user state, as chat history is strictly volatile in-memory only and the database contains only static university brochure details.
2. **Standard Compliance**: Verified zero en/em-dashes exist in active specification updates and verified all file pathways are correct.
