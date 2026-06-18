# Phase 7: Build & Asset Packaging Plan

Define the Play Asset Delivery (PAD) module configuration, gradle build tasks, model asset staging pipelines, and offline database packaging in the Build Guide document.

## User Review Required

> [!IMPORTANT]
> This phase outlines how the 2.58 GB `gemma-4-E2B-it` model file is packaged and delivered to the Android device without exceeding Google Play's 150 MB APK limit.
>
> We will draft [BUILD-GUIDE.md](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md) specifying the `on-demand` asset pack setup, gradle configurations, and model file storage mapping.

## Proposed Changes

### Spec Scaffolding

#### [NEW] [BUILD-GUIDE.md](file:///d:/Github/android-llm/specs/BUILD-GUIDE.md)
* Create the Build Guide document detailing:
  * Google Play Asset Delivery (PAD) implementation details (`on-demand` delivery mode).
  * Gradle Module Directory Structure (defining the `:model_asset` asset pack module).
  * build.gradle configurations (defining asset pack dependencies and Google Play Core libraries).
  * Model asset installation validation workflow (Play Asset Manager integration).
  * Offline database packaging (`admission.db` stored in `/assets/` and decrypted on first launch).

#### [MODIFY] [tasks/TASKS.md](file:///d:/Github/android-llm/specs/tasks/TASKS.md)
* Update progress status of Phase 7 and define tasks for Build and Asset Packaging.

## Verification Plan

### Manual Verification
1. Verify `specs/BUILD-GUIDE.md` follows quality rules (no m-dashes, simple language, tables for config options).
2. Check that the PAD configuration resolves the Play Store 150 MB size limit issue.
3. Pause and request user approval for Phase 7 Build and Asset Packaging.

