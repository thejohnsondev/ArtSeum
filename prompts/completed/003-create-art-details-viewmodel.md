<objective>
Create the `ArtDetailsViewModel` in the shared presentation layer to support the Art Details screen.
This ViewModel must follow the exact architectural pattern established in `ArtListViewModel`.
</objective>

<context>
The ArtSeum project uses a Shared Business Logic architecture (KMP).
The `ArtDetailsViewModel` will drive the UI for the Details Screen.
Reference pattern: `@presentation/src/commonMain/kotlin/com/thejohnsondev/presentation/ArtListViewModel.kt`
</context>

<requirements>
The ViewModel must expose state and handle actions for the following UI sections:

1.  **Immersive Header (ArtworkDisplay)**
    *   State for the current image.
    *   Support for swiping/viewing multiple images.
    *   Navigation handling (Back action).

2.  **Primary Info Block**
    *   Title.
    *   Artist & Date (e.g., "Leonardo da Vinci · 1509").
    *   Status Badge logic (e.g., `isOnView` -> "On View - Gallery 209").

3.  **About Section**
    *   Description text.
    *   Facts Grid data: Medium, Dimensions, Style, Place.

4.  **History Section**
    *   `exhibitionHistory` and `publicationHistory` data.

5.  **Location Map**
    *   Logic to determine if Location section should show (check lat/long not null).
    *   Provide coordinates and `placeOfOrigin` text.

**General:**
*   Use Coroutines & Flow.
*   Follow Clean Architecture (Use Cases -> ViewModel).
*   Ensure the State object is immutable and consumed via StateFlow/Flow.
</requirements>

<implementation>
1.  **Analyze Pattern:** Read `@presentation/src/commonMain/kotlin/com/thejohnsondev/presentation/ArtListViewModel.kt` to understand the base class, state management (StateFlow/State), and action handling pattern.
2.  **Define State:** Create a `ArtDetailsState` data class within the file (or separate if the project dictates) that includes all fields required by the UI design above.
3.  **Create ViewModel:** Implement `ArtDetailsViewModel` in the same package.
    *   It should likely take an `artworkId` or the `ArtObject` itself as a dependency (or a UseCase to get it). *Decision:* If the architecture fetches details, inject a UseCase. If it passes data, define the `init`.
    *   Implement the backing logic to map the domain model to the UI state.
4.  **Handle Actions:** Define an `ArtDetailsAction` sealed interface (if the pattern uses MVI/Actions) for events like "Back Clicked", "Image Swiped", "Toggle History".
</implementation>

<output>
Create:
- `./presentation/src/commonMain/kotlin/com/thejohnsondev/presentation/ArtDetailsViewModel.kt`
</output>

<verification>
Before declaring complete, verify:
- The class structure matches `ArtListViewModel`.
- All UI fields (Title, Artist, Date, OnView, Description, Facts, History, Location) are represented in the State.
- The code compiles and imports are correct for KMP (commonMain).
</verification>
