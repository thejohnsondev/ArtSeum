package com.thejohnsondev.data

import com.thejohnsondev.domain.Artwork
import com.thejohnsondev.domain.ArtworkThumbnail
import com.thejohnsondev.network.api.models.ArtworkData
import com.thejohnsondev.network.api.models.Thumbnail

fun ArtworkData.toDomainModel(): Artwork {
    return Artwork(
        id = this.id,
        title = this.title,
        artist = this.artistDisplay.ifBlank { "Unknown Artist" },
        date = this.dateDisplay,
        medium = this.mediumDisplay,
        description = this.shortDescription ?: this.description,
        imageId = this.imageId.takeIf { it.isNotBlank() },
        department = this.departmentTitle,
        isPublicDomain = this.isPublicDomain,
        creditLine = this.creditLine,
        dimensions = this.dimensions,
        thumbnail = this.thumbnail?.toDomainModel()
    )
}

fun Thumbnail.toDomainModel(): ArtworkThumbnail {
    return ArtworkThumbnail(
        lqip = this.lqip,
        width = this.width,
        height = this.height,
        altText = this.altText
    )
}