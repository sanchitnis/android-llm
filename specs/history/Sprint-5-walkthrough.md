# Walkthrough: Sprint 5 (UI/UX & End-to-End Chat Flow)

This walkthrough documents the completion of the UI/UX components and integration with the Dagger Hilt ViewModel for the Admission Counselor app.

**Document Date**: June 18, 2026

---

## 1. Summary of Changes

We constructed the complete visual user interface in Jetpack Compose, fully respecting the REVA University corporate brand guidelines. The following files were created or modified:

1.  **[Color.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/theme/Color.kt) [NEW]**: Declares brand colors, including `RevaOrange` (`#f7a35b`), `RevaGrey` (`#4a4c55`), and backing scale values.
2.  **[Type.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/theme/Type.kt) [NEW]**: Establishes typography mappings, loading custom font resources for Plus Jakarta Sans and Glacial Indifference.
3.  **[Theme.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/theme/Theme.kt) [NEW]**: Instantiates the Compose `AdmissionCounselorTheme` light color scheme binding.
4.  **[ChatScreen.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/ui/ChatScreen.kt) [NEW]**: Implements the scrollable column list layout (`LazyColumn`), student bubbles (grey background, right-aligned), counselor bubbles (white background, orange left accent edge, left-aligned), loading indicator overlays, inactivity reload banners, and input control sections.
5.  **[MainActivity.kt](file:///d:/Github/android-llm/app/src/main/java/com/admission/counselor/MainActivity.kt) [MODIFY]**: Injects the view model using `hiltViewModel()` and renders the `ChatScreen` content root inside `AdmissionCounselorTheme`.
6.  **[ChatScreenTest.kt](file:///d:/Github/android-llm/app/src/androidTest/java/com/admission/counselor/ui/ChatScreenTest.kt) [NEW]**: Adds instrumented Compose UI tests checking brand text elements and model unloaded banner transitions.

---

## 2. Technical UI/UX Implementation Details

### 2.1 Brand Identity Integration
*   **Typography**: Headers and action labels use Plus Jakarta Sans. Conversational body texts are rendered in Glacial Indifference.
*   **Colors**: Primary branding accents and send buttons are styled with REVA Orange (`#f7a35b`). App header backgrounds and student message containers use REVA Grey (`#4a4c55`).
*   **Header Logo Spacing**: The header bar enforces a minimum padding of 16dp around logo elements, satisfying clear space constraints.

### 2.2 Unidirectional Flow & View State Binding
The `ChatScreen` Composable collects StateFlow states from `CounselorViewModel`:
*   **Idle**: Displays the empty chat screen with standard greeting header.
*   **LoadingModel**: Intercepts keyboard input and overlays an orange circular progress loader with descriptive loading state text.
*   **Generating**: Renders the counselor message container containing live streaming token values (`streamingText`) and a small progress bar.
*   **ModelUnloaded**: Appends a grey status banner explaining the release reason (e.g. inactivity, background timeout) and a reload button to restart the local model.
*   **Error**: Displays a custom card alerting the user to errors with a retry action button.

### 2.3 Conversational Scrolling Efficiency
The LazyColumn list uses a `rememberLazyListState`. A side effect (`LaunchedEffect`) watches the chat size and streaming updates. It automatically scrolls the list view downward as new tokens or messages arrive, keeping the latest response fully visible.

---

## 3. UI Verification Plan Results

We ran automated assertions verifying:
1.  **UI-BRD-001 (Header Elements)**: Renders the header bar and confirms that "NAAC A+", "REVA Admission Counselor", and "REVA" are displayed properly.
2.  **UI-BRD-002 (Unloaded Banner)**: Sets the state to `ModelUnloaded` and asserts the warning text and reload button are rendered.
