# ArtSeum Project Context

## Project Overview
ArtSeum is a Kotlin Multiplatform (KMP) application for browsing art museums. It implements a **Shared Business Logic** architecture where the Domain, Data, and Presentation (ViewModels) layers are shared, while the UI is implemented natively for each platform.

## Tech Stack
*   **Language:** Kotlin (Common/Android), Swift (iOS).
*   **Architecture:** Clean Architecture + MVVM.
*   **Dependency Injection:** Koin (v4.x).
*   **Network:** Ktor (v3.x).
*   **Concurrency:** Kotlin Coroutines & Flow.
*   **Image Loading:** Coil 3.
*   **Serialization:** Kotlinx Serialization.
*   **Interop:** SKIE (Swift Kotlin Interface Enhancer).

## Module Structure
*   **`composeApp`**: Android application entry point. Uses Jetpack Compose.
*   **`iosApp`**: iOS application entry point. Uses SwiftUI.
*   **`shared`**: The main KMP bridge. Exports `core`, `domain`, and `presentation` to the iOS framework.
*   **`presentation`**: Contains Shared ViewModels and UI state logic.
*   **`domain`**: Pure Kotlin business logic (UseCases, Models, Repository Interfaces).
*   **`data`**: Repository implementations, API clients (Ktor), Database (if any).
*   **`core`**:
    *   `:common`: Shared utilities.
    *   `:network`: Ktor setup and networking primitives.
    *   `:ui`: Shared UI resources (branding, tokens) and Compose utilities.

## UI Architecture
*   **Android:** Jetpack Compose. Single Activity architecture.
    *   Theme: Material3.
*   **iOS:** SwiftUI.
    *   Observes shared ViewModels via wrappers.

## Development Conventions
*   **Code Style:** Official Kotlin Style Guide.
*   **JDK:** JDK 11 is used for compilation.
*   **Android SDK:** Compile/Target SDK 36, Min SDK 24.
*   **Formatting:** Follows `.editorconfig` (if present) or standard Android Studio formatting.

## Key Commands
### Build & Run
*   **Build Android App:** `./gradlew :composeApp:assembleDebug`
*   **Run Unit Tests:** `./gradlew test`
*   **Run Android Tests:** `./gradlew connectedAndroidTest`

### Lint & Check
*   **Lint:** `./gradlew lint`

## Important Notes
*   The project uses **Version Catalogs** (`gradle/libs.versions.toml`) for dependency management.
*   **SKIE** is enabled in the `shared` module to improve Swift interop (e.g., Flows as AsyncSequences).
*   Ensure the local environment is set up with JDK 11+.
