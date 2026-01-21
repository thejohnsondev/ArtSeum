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
    val thumbnail: ArtworkThumbnail?
) {
    companion object {
        private val iiifUrl = "https://www.artic.edu/iiif/2"
        private val imageId = "28e2c365-a8c5-37b8-e113-28b96a06b6a4"
        private val altImageIds = listOf(
            "675f8eab-2976-0cea-aeec-d7ab4b78dea9",
            "47b0e70c-f38e-411e-7953-e82a09d47054"
        )
        val demo = Artwork(
            id = 272694,
            title = "Divine Proportion",
            artist = "Leonardo da Vinci (Italian, 1452-1519)\nWritten by Luca Pacioli (Italian, 1445-1517)",
            date = "Venice: Paganinus de Paganinus, 1509",
            medium = "Letterpress and woodcut in black with hand lettering in red ink...",
            description = "A work made of letterpress and woodcut...",
            imagesUrls = listOf(
                "$iiifUrl/$imageId/full/843,/0/default.jpg",
                "$iiifUrl/675f8eab-2976-0cea-aeec-d7ab4b78dea9/full/843,/0/default.jpg"
            ),
            department = "Prints and Drawings", // Assumed from context of the artist
            isPublicDomain = true, // Assumed default for verification
            creditLine = "Gift of...", // Placeholder
            dimensions = "Book: 28.5 × 20.5 cm (11 1/4 × 8 1/16 in.)", // Placeholder based on scale
            thumbnail = ArtworkThumbnail(
                lqip = "data:image/gif;base64,R0lGODlhBwAFAPUAAL+vlr+wmb2zo8CvlsGxmse1m8q4ns26oM27o8+9o82/rNC9o9nJsdrJsd3Ntd/NtN/Pt97PuN7Pud/Ru+HRuuDQu+DRu+LSveXVvOnWvN7TweDUwOfXwO/ex+/fyPHgx/DgyfLgyPXiygAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAHAAUAAAYhwAHG0pA4AgeQxiGYLAydjUPBORQ+ngxIlABYLg2LhBAEADs=",
                width = 10376,
                height = 7355,
                altText = "A work made of letterpress and woodcut in black with hand lettering in red ink..."
            )
        )
    }
}

data class ArtworkThumbnail(
    val lqip: String,
    val width: Int,
    val height: Int,
    val altText: String
)