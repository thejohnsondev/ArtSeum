<objective>
Implement the iOS wrapper layer for the `ArtDetailsViewModel` to enable usage within SwiftUI views, strictly following the established pattern from the ArtList feature.
</objective>

<context>
This is a Kotlin Multiplatform (KMP) project where the UI is native (SwiftUI for iOS).
We use a wrapper pattern to bridge the shared KMP ViewModels to SwiftUI `ObservableObject`s (or `@Observable` in newer Swift).
The `list` feature already implements this pattern, and we need to replicate it for the `details` feature.

Files to analyze:
- Source ViewModel: @presentation/src/commonMain/kotlin/com/thejohnsondev/presentation/ArtDetailsViewModel.kt
- Pattern Interface: @iosApp/iosApp/list/ArtListViewModelProtocol.swift
- Pattern Implementation: @iosApp/iosApp/list/ArtListViewModelWrapperImpl.swift
- Pattern Mock: @iosApp/iosApp/list/DemoArtListViewModelWrapper.swift
</context>

<requirements>
1.  **Directory Structure**: Create a new directory `iosApp/iosApp/details/` to house these files.
2.  **Protocol**: Create `ArtDetailsViewModelProtocol.swift` defining the interface for the view model (properties for state, functions for intents/actions).
3.  **Implementation**: Create `ArtDetailsViewModelWrapperImpl.swift` that adapts the KMP `ArtDetailsViewModel` to the Swift protocol.
    - It must observe the KMP `StateFlow` and publish changes to SwiftUI.
    - It must forward user actions to the KMP ViewModel.
4.  **Demo/Mock**: Create `DemoArtDetailsViewModelWrapper.swift` with hardcoded data for SwiftUI Previews.
5.  **Naming & Style**: Strictly adhere to the naming conventions and coding style seen in the `list` folder examples.
</requirements>

<implementation>
1.  Read `ArtDetailsViewModel.kt` to identify the `State` properties and public methods (Intents).
2.  Read the `list` folder files to understand how the `StateFlow` collection and `SKIE` (if used) or standard Flow observation is handled.
3.  Generate the three Swift files.
</implementation>

<output>
Create the following files:
- `./iosApp/iosApp/details/ArtDetailsViewModelProtocol.swift`
- `./iosApp/iosApp/details/ArtDetailsViewModelWrapperImpl.swift`
- `./iosApp/iosApp/details/DemoArtDetailsViewModelWrapper.swift`
</output>

<verification>
Ensure that:
- The Swift protocol properties match the KMP State fields types (mapped to Swift types).
- The Wrapper implementation compiles and correctly imports the shared module.
- The Demo wrapper provides valid dummy data for all state properties.
</verification>
