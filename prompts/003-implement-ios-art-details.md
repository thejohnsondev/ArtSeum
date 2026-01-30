<objective>
Implement the `ArtDetailsScreen` in the iOS app (SwiftUI) to display artwork details. This screen must be visually IDENTICAL to the Android Compose implementation and use the existing `ArtDetailsViewModelProtocol` for data. Implement smooth navigation from the list screen to this new details screen.
</objective>

<context>
The project shares business logic via KMP. The UI is native.
- **Reference UI (Android):** `@composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/screens/details/ArtworkDetailsScreen.kt` (Source of truth for design)
- **Data Source:** `@iosApp/iosApp/details/ArtDetailsViewModelProtocol.swift`
- **Navigation/Pattern Reference:** `@iosApp/iosApp/list/ArtListScreen.swift` (Follow this pattern for ViewModel observation and UI structure)
- **Target Location:** `@iosApp/iosApp/details/ArtDetailsScreen.swift` (Create this file)
</context>

<requirements>
1.  **Visual Parity:** The iOS screen must look identical to the Android `ArtworkDetailsScreen`. Meticulously replicate the layout, typography, spacing, and image placement.
2.  **Data Integration:** Use `ArtDetailsViewModelProtocol` to populate the UI. Do not mock data if the ViewModel provides it.
3.  **Navigation:** 
    - Modify `ArtListScreen.swift` to navigate to `ArtDetailsScreen` when an item is clicked.
    - Ensure the transition is smooth and idiomatic for iOS (e.g., `NavigationLink`).
4.  **Architecture:** Follow the project's existing pattern for observing ViewModels in SwiftUI (likely a Wrapper/ObservableObject pattern as seen in `ArtListScreen`).
</requirements>

<implementation>
1.  **Analyze Reference:**
    - Read `@composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/screens/details/ArtworkDetailsScreen.kt` to understand the UI hierarchy (Column, Row, Image, Text styles).
    - Read `@iosApp/iosApp/list/ArtListScreen.swift` to understand how to observe the ViewModel and implement navigation.
    - Read `@iosApp/iosApp/details/ArtDetailsViewModelProtocol.swift` to see available properties/methods.

2.  **Create Details Screen:**
    - Create `@iosApp/iosApp/details/ArtDetailsScreen.swift`.
    - Implement the UI using SwiftUI components that map to the Compose counterparts (e.g., `VStack` for `Column`, reuse `ArtDisplay` at the very top, just like on android).

3.  **Integrate Navigation:**
    - Update `@iosApp/iosApp/list/ArtListScreen.swift` to wrap list items in a navigation link pointing to `ArtDetailsScreen`, passing the necessary ID or object.

4.  **Polishing:**
    - Ensure fonts, colors, and margins match the project's design system (check `iosApp/iosApp/Assets.xcassets` or Color/Font extensions if available).
</implementation>

<output>
- Create: `./iosApp/iosApp/details/ArtDetailsScreen.swift`
- Modify: `./iosApp/iosApp/list/ArtListScreen.swift`
</output>

<verification>
Before declaring completion:
- Verify that `ArtDetailsScreen.swift` references the `ArtDetailsViewModelProtocol`.
- Verify that `ArtListScreen.swift` contains the navigation logic.
- Ensure the code compiles (conceptually, or via `xcodebuild` if available/configured, though mostly visual verification of code structure against requirements).
</verification>
