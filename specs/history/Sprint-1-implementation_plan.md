# Sprint 1: Project Scaffold & Asset Delivery

This implementation plan outlines the steps to build the foundation of the Android Admission Counselor AI app, focusing on the project structure, dependency injection, and Play Asset Delivery (PAD).

## User Review Required

> [!IMPORTANT]
> **Dependency Injection Choice**: The specs mention "Hilt/Koin". I propose using **Dagger Hilt** as it provides robust compile-time safety and integrates seamlessly with Android ViewModels and Jetpack Compose. Please confirm if Hilt is acceptable.

## Open Questions

> [!WARNING]
> Since I operate in a command-line environment without the Android Studio UI, I will generate the base Android project structure (`settings.gradle.kts`, `build.gradle.kts`, `AndroidManifest.xml`, `MainActivity.kt`, etc.) manually by writing the files. Please let me know if you prefer to scaffold the project yourself using Android Studio and have me take over the Kotlin logic.

## Proposed Changes

### 1. Project & Gradle Scaffolding
Establish the multi-module project structure as defined in the `BUILD-GUIDE.md`.

#### [NEW] `settings.gradle.kts` & `build.gradle.kts`
- Root project configurations.
- Define plugin management for Android application, Kotlin Android, KSP (for Room/Hilt), and Hilt.

#### [NEW] `app/build.gradle.kts`
- Application module setup.
- Dependencies: Jetpack Compose, Dagger Hilt, Room, Play Core (for PAD), Coroutines.

#### [NEW] `model-asset-pack/build.gradle.kts`
- Asset pack module configuration with `com.android.asset-pack` plugin configured for `install-time` delivery.

### 2. Core App Framework
#### [NEW] `app/src/main/AndroidManifest.xml`
- Basic manifest definition. The app will be completely offline, so no `<uses-permission android:name="android.permission.INTERNET" />` will be added.

#### [NEW] `app/src/main/java/com/admission/counselor/CounselorApp.kt`
- Custom `Application` class annotated with `@HiltAndroidApp`.

#### [NEW] `app/src/main/java/com/admission/counselor/MainActivity.kt`
- Entry point with `@AndroidEntryPoint` and a placeholder Compose initialization screen.

### 3. Dependency Injection (Hilt)
#### [NEW] `app/src/main/java/com/admission/counselor/di/AppModule.kt`
- Hilt module to provide singletons for the `RoomDatabase` and the `AssetDeliveryManager`.

### 4. Play Asset Delivery (PAD)
#### [NEW] `app/src/main/java/com/admission/counselor/data/AssetDeliveryManager.kt`
- Implementation of the Play Core API to request the `gemma-4-E2B-it.litertlm` asset pack.
- Exposes a `StateFlow<AssetDeliveryState>` indicating `Pending`, `Downloading(progress)`, `Completed(path)`, or `Error`.

### 5. Room Database Scaffolding
#### [NEW] `app/src/main/java/com/admission/counselor/data/db/AdmissionDatabase.kt`
- Scaffold for the local SQLite database. The actual schemas will be fleshed out in Sprint 3.

### 6. Integration Tests
#### [NEW] `app/src/androidTest/java/com/admission/counselor/AssetDeliveryManagerTest.kt`
- Implement `IT-PAD-001` and `IT-PAD-002` to verify state transitions based on mocked Play Core interactions.

## Verification Plan

### Automated Tests
- Run `./gradlew test` to ensure project compilation and module setup.
- Run the PAD integration tests locally.

### Manual Verification
- We will execute `./gradlew assembleDebug` to verify the multi-module structure builds a valid APK and the asset pack binds correctly.
