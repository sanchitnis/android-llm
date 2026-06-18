# Phase 5: App UI & ViewModel Design Plan

Define the Jetpack Compose user interface layout, ViewModel state-machine states, token streaming flows, and runtime UI-to-engine lifecycle interactions in the App Design document.

## User Review Required

> [!IMPORTANT]
> This phase bridges the underlying LiteRT-LM engine with the user-facing chat interface.
>
> We will draft [APP-DESIGN.md](file:///d:/Github/android-llm/specs/APP-DESIGN.md) specifying ViewModel state flows, Composable structures, and error boundary handling.

## Proposed Changes

### Spec Scaffolding

#### [NEW] [APP-DESIGN.md](file:///d:/Github/android-llm/specs/APP-DESIGN.md)
* Create the App Design document detailing:
  * Chat UI View Model State-Machine (Idle, LoadingModel, Ready, Generating, Error).
  * Jetpack Compose Composable UI hierarchy and layouts (ChatScreen, ChatBubble, InputBar, StatusBanner).
  * Streaming data flow: `Flow<String>` collection, state buffering, and partial token rendering.
  * User action flows (Submit query, Stop generation, Clear chat, Retry after memory unload).

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* Update progress status of Phase 5 and define tasks for App UI Design.

## Verification Plan

### Manual Verification
1. Verify `specs/APP-DESIGN.md` follows quality rules (no m-dashes, simple language, tables for UI components).
2. Check that the UI state-machine handles all engine conditions (busy state rejection, low-memory unloads, idle timeouts).
3. Pause and request user approval for Phase 5 App UI and ViewModel Design.

