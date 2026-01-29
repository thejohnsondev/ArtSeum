package com.thejohnsondev.domain.model

data class Artwork(
    val id: Int,
    val title: String,
    val artist: String,
    val date: String,
    val medium: String,
    val description: String?,
    val imagesUrls: List<String>?,
    val department: String,
    val isPublicDomain: Boolean,
    val creditLine: String,
    val dimensions: String,
    val thumbnail: ArtworkThumbnail?,
    val latitude: Double?,
    val longitude: Double?,
    val placeOfOrigin: String,
    val galleryTitle: String?,
    val isOnView: Boolean,
    val style: String?,
    val classification: String?,
    val exhibitionHistory: String?,
    val publicationHistory: String?
) {
    companion object {
        private const val IIIF_URL = "https://www.artic.edu/iiif/2"
        private const val DEMO_IMAGE_ID = "28e2c365-a8c5-37b8-e113-28b96a06b6a4"

        val demo = Artwork(
            id = 272694,
            title = "Divine Proportion",
            artist = "Leonardo da Vinci (Italian, 1452-1519)",
            date = "1509",
            medium = "Letterpress and woodcut in black...",
            description = "A work made of letterpress and woodcut...",
            imagesUrls = listOf(
                "$IIIF_URL/$DEMO_IMAGE_ID/full/843,/0/default.jpg",
                "$IIIF_URL/675f8eab-2976-0cea-aeec-d7ab4b78dea9/full/843,/0/default.jpg"
            ),
            department = "Prints and Drawings",
            isPublicDomain = true,
            creditLine = "Gift of...",
            dimensions = "Book: 28.5 × 20.5 cm",
            thumbnail = ArtworkThumbnail(
                lqip = "data:image/gif;base64,R0lGODlhBwAFAPUAAL+vlr+wmb2zo8CvlsGxmse1m8q4ns26oM27o8+9o82/rNC9o9nJsdrJsd3Ntd/NtN/Pt97PuN7Pud/Ru+HRuuDQu+DRu+LSveXVvOnWvN7TweDUwOfXwO/ex+/fyPHgx/DgyfLgyPXiygAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAHAAUAAAYhwAHG0pA4AgeQxiGYLAydjUPBORQ+ngxIlABYLg2LhBAEADs=",
                width = 10376,
                height = 7355,
                altText = "Demo Alt Text"
            ),
            // Demo New Fields
            latitude = 41.8795845,
            longitude = -87.6237126,
            placeOfOrigin = "Venice",
            galleryTitle = "Gallery 209",
            isOnView = true,
            style = "Renaissance",
            classification = "Book",
            exhibitionHistory = "Exhibited in Chicago, 2010...",
            publicationHistory = "Published in 1509..."
        )
    }
}

data class ArtworkThumbnail(
    val lqip: String,
    val width: Int,
    val height: Int,
    val altText: String
)