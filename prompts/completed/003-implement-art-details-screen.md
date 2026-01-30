<objective>
Implement the `ArtDetailsScreen` composable in the shared `composeApp` module.
This screen displays detailed information about a selected artwork, prioritizing visual impact and providing context through an immersive layout.
</objective>

<context>
The application is a Kotlin Multiplatform project using Jetpack Compose.
You need to create a new screen that opens when an item is clicked in `@composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/screens/list/ArtworkListScreen.kt`.

**Key Resources:**
- Pattern Source: `@composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/screens/list/ArtworkListScreen.kt` (Follow this coding style and structure)
- Data Source: `@presentation/src/commonMain/kotlin/com/thejohnsondev/presentation/ArtDetailsViewModel.kt` (Use this for state and logic)
- Design Goal: Visually immersive, informative, and clean. Black background, same style like in the List screen. Use colors from Colors.colorScheme.*. Follow the design language of the ArtListScreen.kt

**Target File:**
- `composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/screens/details/ArtDetailsScreen.kt` (Create this file/package if needed)
</context>

<requirements>
Implement the UI with the following distinct sections and behaviors:

1.  **Immersive Header (The "ArtworkDisplay")**
    -   **Layout**: `ArtworkDisplay` component at the very top.
    -   **Status Bar**: Image must extend behind the status bar (edge-to-edge). Status bar icons must be visible.
    -   **Interaction**: Enable horizontal swipe to view multiple images (reuse existing logic).
    -   **Navigation**: Overlay a "Back" button (icon) on the top-left, ensuring it is clickable and visible.

2.  **Primary Info Block** (Immediately below images)
    -   **Title**: Large, bold typography.
    -   **Artist & Date**: Secondary text color, slightly smaller (e.g., "Leonardo da Vinci · 1509").
    -   **Status Badge**: Display a small pill/badge (e.g., "On View - Gallery 209") *only* if `isOnView` is true.

3.  **The "About" Section**
    -   **Description**: Text description of the artwork.
    -   **Truncation**: If text is long (HTML from API), truncate initially with a "Read More" toggle to save space.
    -   **Facts Grid**: A 2-column grid displaying:
        -   Medium (e.g., Oil on Canvas)
        -   Dimensions (e.g., 50 x 60 cm)
        -   Style (e.g., Impressionism)
        -   Place (e.g., France)

4.  **The History Section (Collapsible)**
    -   **Accordion Style**: Create expandable sections for "Exhibition History" and "Publication History".
    -   **Default State**: Collapsed to keep UI clean.

5.  **The Location Map (Bottom)**
    -   **Condition**: Show *only* if `latitude` and `longitude` are not null.
    -   **Header**: "Location" or "Place of Origin".
    -   **Map View**: A square or 16:9 map component centered on coordinates.
        -   *Note*: Check if a Map library is available. If not, create a distinct placeholder UI that can be easily replaced with a native map implementation later.
    -   **Marker**: Pin at the exact location.
    -   **Context**: Small text label below map (e.g., "Venice, Italy").
</requirements>

<implementation>
-   **Architecture**: Follow the MVI/MVVM pattern used in `ArtworkListScreen`.
    -   Inject `ArtDetailsViewModel`.
    -   Collect UI state.
    -   Pass events back to the ViewModel.
-   **Styling**: Use the project's design system (Theme, Colors, Typography) defined in `:core:ui`.
-   **Composables**: Break down complex sections (Header, Info, Map) into private sub-composables within the file for readability.
-   **Safety**: Handle null values gracefully for all optional fields (dimensions, style, history, etc.).
</implementation>

<output>
Create the following file:
- `./composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/screens/details/ArtDetailsScreen.kt`
</output>

<verification>
Before declaring complete:
1.  Verify that the `ArtDetailsViewModel` is correctly integrated.
2.  Ensure the "Back" button navigation logic is hooked up.
3.  Check that conditional visibility works for the "On View" badge and "Location Map".
</verification>
