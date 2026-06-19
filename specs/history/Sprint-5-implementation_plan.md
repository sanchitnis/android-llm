# Sprint 5: UI/UX & End-to-End Chat Flow

This implementation plan focuses on constructing the user interface using Jetpack Compose, incorporating the official REVA University brand colors and typography guidelines, and binding the views to the `CounselorViewModel` state machine.

---

## User Review Required

> [!IMPORTANT]
> **Custom Font Resources**: The code references custom font resource files (`R.font.plus_jakarta_sans_bold`, `R.font.plus_jakarta_sans_medium`, and `R.font.glacial_indifference_regular`). If these font files are missing from the resource folder during build, the compilation will fail. I will configure the typography definitions using these custom resource files, but we should make sure the `.ttf` files are added to `app/src/main/res/font/` before compiling in Android Studio.

---

## Proposed Changes

### 1. REVA Branding Theme Configuration
#### [NEW] [Color.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/theme/Color.kt)
- Define `RevaOrange` (`#f7a35b`) and `RevaGrey` (`#4a4c55`).
#### [NEW] [Type.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/theme/Type.kt)
- Declare `FontFamily` definitions for Plus Jakarta Sans and Glacial Indifference.
- Define typography styles for titles, labels, and message body text.
#### [NEW] [Theme.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/theme/Theme.kt)
- Implement `AdmissionCounselorTheme` setting up light color schemes.

### 2. Chat Views
#### [NEW] [ChatScreen.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/ChatScreen.kt)
- Build the main Composable layout containing:
  - `HeaderBar`: Custom grey header with NAAC A+ logo on the left and REVA wordmark on the right.
  - `MessageList`: LazyColumn scrolling the conversation.
  - `ChatBubble`: Student message bubble (right-aligned, grey background) and Counselor bubble (left-aligned, white background with orange left border).
  - `StatusBanner`: Reload banner if the model is unloaded.
  - `InputBar`: Writable input field and orange Send icon.
  - `LoadingIndicator`: Orange progress indicator when loading the model.

### 3. Main View Binding
#### [MODIFY] [MainActivity.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/MainActivity.kt)
- Integrate Hilt's `hiltViewModel<CounselorViewModel>()`.
- Wrap the activity content inside `AdmissionCounselorTheme` and display `ChatScreen`.

### 4. UI/UX Verification Tests
#### [NEW] [ChatScreenTest.kt](file:///d:/Github/android-llm/app/src/androidTest/java/com/admission/counselor/ui/ChatScreenTest.kt)
- Implement UI tests verifying brand guidelines:
  - Check that the header background color is REVA Grey.
  - Check that the send button background color is REVA Orange.
  - Verify state transitions (e.g. model loading displays loading progress, memory unloaded displays reload banner).

---

## Verification Plan

### Automated Tests
- Run Android UI tests verifying that colors, typography sizes, and text layouts meet design specifications.

### Manual Verification
- Open the project in Android Studio, sync Gradle, and install the application on an emulator or real device.
- Interact with the counselor: type queries, verify that responses stream in real-time, click reload on the idle banner, and verify that the layout does not block the UI thread.
