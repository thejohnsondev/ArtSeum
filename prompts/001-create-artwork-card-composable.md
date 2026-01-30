<objective>
Create a Jetpack Compose composable named `ArtworkCard` that displays a single artwork item, designed for a Kotlin Multiplatform project.
</objective>

<context>
The project uses Jetpack Compose for the UI. You must adhere to the project's specific conventions defined in `GEMINI.md`, especially regarding theming from the `:core:ui` module.

The composable will display data based on the following models:
```kotlin
data class Artwork(
    val id: Int,
    val title: String,
    val artist: String,
    val date: String,
    val medium: String,
    val description: String?,
    val mainImageUrl: String?,
    val restImagesUrls: List<String>?,
    val department: String,
    val isPublicDomain: Boolean,
    val creditLine: String,
    val dimensions: String,
    val thumbnail: ArtworkThumbnail?
)

data class ArtworkThumbnail(
    val lqip: String, // A Base64 encoded low-quality image placeholder
    val width: Int,
    val height: Int,
    val altText: String
)
```
</context>

<requirements>
1.  **Image Display:**
    *   The main content of the composable is an image carousel.
    *   Use a modern image loading library (like Coil) suitable for KMP.
    *   Initially, display a low-quality placeholder decoded from the Base64 `lqip` string in `ArtworkThumbnail`.
    *   Load the high-quality image from `mainImageUrl` to replace the placeholder.

2.  **Image Carousel:**
    *   The carousel should be horizontally swipeable.
    *   The images in the carousel are the `mainImageUrl` followed by all URLs in `restImagesUrls`.
    *   Implement a pager indicator (small white dots) to show the current image position in the carousel.

3.  **Overlay and Text:**
    *   Render a gradient overlay that starts transparent at the top and transitions to a solid color at the bottom, covering the lower portion of the image. The solid color should be from the app's theme (`Colors.colorScheme...`).
    *   On top of this gradient, display the artwork's details:
        *   `title` in **bold**.
        *   `artist` on the line below the title.
        *   `date` below the artist.
    *   Ensure the text is legible against the gradient and does not overwhelm the UI with too much information.

4.  **Styling:**
    *   The composable must have a flat appearance.
    *   **No rounded corners**: Use `shape = RectangleShape`.
    *   **No shadow**: Use `elevation = 0.dp`.

</requirements>

<implementation>
*   The composable signature should be `fun ArtworkCard(artwork: Artwork, modifier: Modifier = Modifier)`.
*   Use `HorizontalPager` for the image carousel.
*   For the placeholder, decode the `lqip` Base64 string into a bitmap and display it.
*   Structure your code logically. Consider creating smaller private composables for the `ImageCarousel` and `ArtworkDetailsOverlay` for better readability and maintenance.
*   Strictly adhere to the project's theming. Use color and dimension resources from the `:core:ui` module as specified in `GEMINI.md`.

</implementation>

<output>
Create a new file for the composable at the following path:
- `composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/presentation/component/ArtworkCard.kt`
</output>

<verification>
Ensure the generated code is clean, idiomatic Kotlin, and compiles successfully. The composable should strictly follow all functional and styling requirements.
</verification>

<success_criteria>
- The `ArtworkCard.kt` file is created at the specified path.
- The composable correctly displays the `lqip` placeholder while the main image is loading.
- The image carousel works, including `mainImageUrl` and `restImagesUrls`, and shows dot indicators.
- A gradient is displayed over the bottom of the image.
- Title (bold), artist, and date are displayed correctly on the gradient.
- The composable has no rounded corners or shadow.
- The implementation respects project conventions and theming from `:core:ui`.
</success_criteria>
