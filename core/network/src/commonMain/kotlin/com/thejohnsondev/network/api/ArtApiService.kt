package com.thejohnsondev.network.api

import com.thejohnsondev.network.api.models.ArtResponse
import com.thejohnsondev.network.api.models.ArtSearchResponse
import com.thejohnsondev.network.api.models.ArtworkListResponse

interface ArtApiService {
    suspend fun fetchArtworks(
        page: Int,
        limit: Int,
    ): Result<ArtworkListResponse>

    suspend fun fetchArtworkById(artworkId: String): Result<ArtResponse>

    suspend fun searchArtworks(
        query: String,
        page: Int,
        limit: Int,
    ): Result<ArtSearchResponse>
}