package com.thejohnsondev.network.api

import com.thejohnsondev.network.api.models.ArtResponse
import com.thejohnsondev.network.api.models.ArtworkListResponse

interface ArtApi {
    suspend fun fetchArtworks(
        page: Int,
        limit: Int,
    ): Result<ArtworkListResponse>

    suspend fun fetchArtworkById(artworkId: String): Result<ArtResponse>

    suspend fun searchArtworks(
        query: String
    ): Result<ArtworkListResponse>
}