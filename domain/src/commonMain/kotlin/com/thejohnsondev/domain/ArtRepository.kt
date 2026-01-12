package com.thejohnsondev.domain

import com.thejohnsondev.domain.model.Artwork
import kotlinx.coroutines.flow.Flow

interface ArtRepository {

    val artworks: Flow<List<Artwork>>

    suspend fun fetchArtworks(
        page: Int,
        limit: Int,
    ): Result<Unit>

    suspend fun fetchArtworkById(artworkId: String): Result<Artwork>

    suspend fun searchArtworks(
        query: String
    ): Result<List<Artwork>>
}