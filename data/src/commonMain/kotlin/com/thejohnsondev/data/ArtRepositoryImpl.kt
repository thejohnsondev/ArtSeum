package com.thejohnsondev.data

import com.thejohnsondev.domain.Artwork
import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.network.api.ArtApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArtRepositoryImpl(
    private val api: ArtApiService
): ArtRepository {

    private val _artworks = MutableStateFlow<List<Artwork>>(emptyList())
    override val artworks: Flow<List<Artwork>> = _artworks.asStateFlow()

    override suspend fun fetchArtworks(
        page: Int,
        limit: Int
    ): Result<Unit> {
        val result = api.fetchArtworks(page, limit)

        return if (result.isSuccess) {
            val remoteData = result.getOrNull()?.data ?: emptyList()
            val domainData = remoteData.map { it.toDomainModel() }
            _artworks.value += domainData
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }

    override suspend fun fetchArtworkById(artworkId: String): Result<Artwork> {
        val artworkResult = api.fetchArtworkById(artworkId)
        if (artworkResult.isFailure) {
            return Result.failure(artworkResult.exceptionOrNull() ?: Exception("Unknown error occurred"))
        }
        val artwork = artworkResult.getOrNull()?.data
            ?: return Result.failure(Exception("Artwork not found"))
        return Result.success(artwork.toDomainModel())
    }

    override suspend fun searchArtworks(query: String): Result<List<Artwork>> {
        val searchResult = api.searchArtworks(query)
        if (searchResult.isFailure) {
            return Result.failure(searchResult.exceptionOrNull() ?: Exception("Unknown error occurred"))
        }
        val artworks = searchResult.getOrNull()?.data ?: emptyList()
        return Result.success(artworks.map { it.toDomainModel() })
    }
}