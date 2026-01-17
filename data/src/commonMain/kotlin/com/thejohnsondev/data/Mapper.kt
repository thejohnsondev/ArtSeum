package com.thejohnsondev.data

import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.ArtworkThumbnail
import com.thejohnsondev.network.api.models.ArtworkData
import com.thejohnsondev.network.api.models.Config
import com.thejohnsondev.network.api.models.Thumbnail

fun ArtworkData.toDomainModel(
    config: Config?
): Artwork {
    val mainImageUrl = this.imageId.takeIf { it.isNotBlank() }?.let { imageId ->
        "${config?.iiifUrl}/$imageId/full/843,/0/default.jpg"
    }
    val restImagesUrls = this.altImageIds.map { imageId ->
        "${config?.iiifUrl}/$imageId/full/843,/0/default.jpg"
    }
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

fun Thumbnail.toDomainModel(): ArtworkThumbnail {
    return ArtworkThumbnail(
        lqip = this.lqip,
        width = this.width,
        height = this.height,
        altText = this.altText
    )
}