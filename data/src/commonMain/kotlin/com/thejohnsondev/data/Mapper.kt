package com.thejohnsondev.data

import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.ArtworkSearchItem
import com.thejohnsondev.domain.model.ArtworkThumbnail
import com.thejohnsondev.network.api.models.ArtworkData
import com.thejohnsondev.network.api.models.ArtworkSearchData
import com.thejohnsondev.network.api.models.Config
import com.thejohnsondev.network.api.models.Thumbnail

fun ArtworkData.toDomainModel(
    config: Config?
): Artwork {
    val mainImageUrl = this.imageId
        ?.takeIf { it.isNotBlank() }
        ?.let { imageId -> "${config?.iiifUrl}/$imageId/full/843,/0/default.jpg" }

    val restImagesUrls = this.altImageIds
        .orEmpty()
        .mapNotNull { id -> id.takeIf { it.isNotBlank() } }
        .map { id -> "${config?.iiifUrl}/$id/full/843,/0/default.jpg" }

    val imagesListUrls = if (mainImageUrl != null) {
        listOf(mainImageUrl) + restImagesUrls
    } else {
        restImagesUrls
    }

    return Artwork(
        id = this.id ?: this.hashCode(),
        title = this.title.orEmpty(),
        artist = this.artistDisplay?.ifBlank { "Unknown Artist" } ?: "Unknown Artist",
        date = this.dateDisplay.orEmpty(),
        medium = this.mediumDisplay.orEmpty(),
        description = null,
        imagesUrls = imagesListUrls,
        department = this.departmentTitle.orEmpty(),
        isPublicDomain = this.isPublicDomain ?: false,
        creditLine = this.creditLine.orEmpty(),
        dimensions = this.dimensions.orEmpty(),
        thumbnail = this.thumbnail?.toDomainModel(),
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

fun ArtworkSearchData.toDomainModel(): ArtworkSearchItem {
    return ArtworkSearchItem(
        id = this.id ?: this.hashCode(),
        title = this.title.orEmpty(),
        thumbnail = this.thumbnail?.toDomainModel()
    )
}