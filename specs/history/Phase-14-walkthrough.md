# Walkthrough: Phase 14 (Windows Security, Data Privacy, and Sandboxing Model)

This walkthrough documents the completion of **Phase 14: Windows Security, Data Privacy, and Sandboxing Model** for the Admission Counselor AI.

**Document Date**: June 21, 2026

---

## 1. Summary of Changes

We finalized the secure storage policies, JNI encryption wrappers, and network isolation filters for the Windows Desktop application. The following files were created or modified:

1. **[WINDOWS-ARCHITECTURE.md](file:///d:/Github/android-llm/specs/WINDOWS-ARCHITECTURE.md) [MODIFY]**:
   - Added Section 6 outlining the filesystem permissions, key storage encryption, memory volatility, and firewall guidelines.
   - Incorporated explicit notes deferring all security implementations to the final sprint (Sprint 13) after functional code is verified.
2. **[tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md) [MODIFY]**: Marked Phase 14 security rules design task as completed.

---

## 2. Locked-Down Security Specifications

### 2.1 File System Sandboxing
- Access permissions on `%LOCALAPPDATA%\AdmissionCounselor` are programmatically restricted to the current user's Security Identifier (Owner SID) and `NT AUTHORITY\SYSTEM` only.

### 2.2 settings.json API Key Protection
- The Priority 2 Gemini API Key is encrypted using Windows Data Protection API (DPAPI) via native dynamic library calls to `CryptProtectData`/`CryptUnprotectData`, matching user logon credentials.

### 2.3 Volatile Transcript In-Memory Safety
- Chats are maintained in-memory inside Kotlin StateFlow buffers.
- Plaintext string retention in the JVM pool is prevented by wrapping prompts in mutable `CharArray` buffers and explicitly running `.fill('\u0000')` immediately after use.

### 2.4 Outbound Traffic Firewall Configuration
- Packaged configuration includes outbound firewall rules restricting executable requests to the Priority 2 API target `generativelanguage.googleapis.com` (port 443).

---

## 3. Verification & Critique Pass

1. **Implementation Precedence Alignment**: Verified that all security mechanisms are explicitly scheduled to be implemented last (in Sprint 13), after all basic functions are tested and operational.
2. **Quality Compliance**: Checked that no Unicode en/em-dashes are used and all path links are correct.
