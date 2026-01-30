<objective>
Implement a specific `ArtworkDisplay` composable (or similarly named UI component) in the shared KMP module. This component must display a single artwork with a carousel of images, "blur-up" loading using a base64 thumbnail, and specific text styling, along with a verified Preview using provided mock data.
</objective>

<context>
This is a Kotlin Multiplatform project.
The component will be used to display details of an artwork.
The project uses Jetpack Compose Multiplatform.
You should check for existing image loading libraries (like Coil) and usage patterns in `build.gradle.kts` and other UI files before implementation.
</context>

<requirements>
1.  **Domain Models**:
    Ensure these models are used (create or reuse if they exist):
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
        val lqip: String,
        val width: Int,
        val height: Int,
        val altText: String
    )
    ```

2.  **UI Layout & Behavior**:
    *   **Image Carousel**:
        *   Use a Pager (e.g., `HorizontalPager`) to swipe between `mainImageUrl` and any URLs in `restImagesUrls`.
        *   **Indicator**: Display white dots representing the current image index.
    *   **Smart Loading (LQIP)**:
        *   The `thumbnail.lqip` property contains a Base64 string.
        *   This Base64 image must be decoded and displayed *immediately* as a placeholder while the full resolution `mainImageUrl` fetches.
    *   **Text & Overlay**:
        *   Place a gradient overlay at the bottom of the image area (transparent to solid).
        *   Display details over/near the bottom: **Title** (Bold), **Artist** (below title), **Date**, and other relevant info (Medium, Dimensions) without overcrowding.
    *   **Styling**:
        *   **Flat Design**: No rounded corners, no shadows.
        *   **Full Width**: Should likely take up available width.

3.  **Preview Implementation**:
    *   Create a Preview function.
    *   Use the **exact JSON data** and **mapping logic** provided below to populate the preview data.

    **Mock Data Source (JSON)**:
    ```json
    {
      "data": {
        "id": 272694,
        "api_model": "artworks",
        "title": "Divine Proportion",
        "thumbnail": {
          "lqip": "data:image/gif;base64,R0lGODlhBwAFAPUAAL+vlr+wmb2zo8CvlsGxmse1m8q4ns26oM27o8+9o82/rNC9o9nJsdrJsd3Ntd/NtN/Pt97PuN7Pud/Ru+HRuuDQu+DRu+LSveXVvOnWvN7TweDUwOfXwO/ex+/fyPHgx/DgyfLgyPXiygAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAHAAUAAAYhwAHG0pA4AgeQxiGYLAydjUPBORQ+ngxIlABYLg2LhBAEADs=",
          "width": 10376,
          "height": 7355,
          "alt_text": "A work made of letterpress and woodcut in black with hand lettering in red ink..."
        },
        "artist_display": "Leonardo da Vinci (Italian, 1452-1519)\nWritten by Luca Pacioli (Italian, 1445-1517)",
        "date_display": "Venice: Paganinus de Paganinus, 1509",
        "medium_display": "Letterpress and woodcut in black...",
        "image_id": "28e2c365-a8c5-37b8-e113-28b96a06b6a4",
        "alt_image_ids": [
          "675f8eab-2976-0cea-aeec-d7ab4b78dea9",
          "47b0e70c-f38e-411e-7953-e82a09d47054"
        ]
        // ... (Refer to the user's full input for other fields if needed)
      },
      "config": {
        "iiif_url": "https://www.artic.edu/iiif/2"
      }
    }
    ```

    **Mapping Logic (Adapt to your file structure)**:
    ```kotlin
    // Use this logic to convert the raw JSON/DTOs to the Domain Model for the preview
    fun ArtworkData.toDomainModel(config: Config?): Artwork {
        val mainImageUrl = this.imageId.takeIf { it.isNotBlank() }?.let { imageId -> "${config?.iiifUrl}/$imageId/full/843,/0/default.jpg" }
        val restImagesUrls = this.altImageIds.map { imageId -> "${config?.iiifUrl}/$imageId/full/843,/0/default.jpg" }
        return Artwork(
            id = this.id,
            title = this.title,
            artist = this.artistDisplay.ifBlank { "Unknown Artist" },
            date = this.dateDisplay,
            medium = this.mediumDisplay,
            description = this.shortDescription ?: this.description,
            mainImageUrl = mainImageUrl,
            restImagesUrls = restImagesUrls,
            department = this.departmentTitle,
            isPublicDomain = this.isPublicDomain,
            creditLine = this.creditLine,
            dimensions = this.dimensions,
            thumbnail = this.thumbnail?.toDomainModel()
        )
    }
    ```
</requirements>

<implementation>
1.  **Dependencies**: Check `@composeApp/build.gradle.kts` for image loading libraries (e.g., Coil). If Coil is present, use `SubcomposeAsyncImage` or `AsyncImage` with a placeholder.
2.  **Base64 Decoding**: Implement a safe way to decode the Base64 string to a `Bitmap` or `ImageBitmap` compatible with Compose Multiplatform.
3.  **File Location**: Create the file in `@composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/ui/` (or a subpackage like `component` or `detail`).
</implementation>

<output>
Create/Modify:
- `./composeApp/src/commonMain/kotlin/com/thejohnsondev/artseum/ui/ArtworkDisplay.kt` (or appropriate name)
</output>

<verification>
- Verify that the `lqip` Base64 string is decoded and shown before the main image loads.
- Verify the carousel dots work correctly.
- Ensure the Preview function compiles and effectively demonstrates the component using the provided data.
</verification>
