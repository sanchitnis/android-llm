# Phase 14: Windows Security & Data Privacy Implementation Plan

Define the detailed Windows security model, DPAPI key storage configurations, filesystem sandboxing policies, and volatile transcript requirements in [WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md).

## User Review Required

> [!IMPORTANT]
> - User API keys will be encrypted using Windows Data Protection API (DPAPI) and stored locally.
> - Workspace directories will be sandboxed to the current user's security identifier (SID).
> - Converational data transcripts will remain volatile (in-memory only) and be wiped on app closing.

## Proposed Changes

### Specifications

#### [MODIFY] [WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md)
Add a new main Section 6 (Security and Privacy Sandboxing) detailing:
- **Section 6.1: User SID-Based Filesystem Sandboxing**: Configuring NTFS permissions using Access Control Entries (ACE) on `%LOCALAPPDATA%\AdmissionCounselor` restricting access to the Owner SID and SYSTEM only (implemented last in Sprint 13).
- **Section 6.2: Windows DPAPI API Key Encryption**: Implementation of C++ JNI wrappers linking to `CryptProtectData` and `CryptUnprotectData` to encrypt the settings JSON (implemented last in Sprint 13).
- **Section 6.3: Volatile Transcript Memory Policy**: Guaranteeing transcripts are stored strictly in-memory using Kotlin StateFlow, with explicit overrides for JVM string cleaning (using char arrays or zeroing out buffers) upon session closures (implemented last in Sprint 13).
- **Section 6.4: Outbound Traffic Restrictions**: Defining firewall rules and client-side sandbox restrictions ensuring outbound network calls are blocked entirely, with the sole exception of the official Priority 2 Gemini API endpoints (implemented last in Sprint 13).

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
Mark Phase 14 tasks as completed.

---

## Verification Plan

### Manual Verification
- Verify security policies adhere strictly to the zero-leak mandates from PRD.
- Run consistency check audits on active `/specs` files ensuring quality rules match.
