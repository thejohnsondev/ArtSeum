package com.thejohnsondev.domain.model

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