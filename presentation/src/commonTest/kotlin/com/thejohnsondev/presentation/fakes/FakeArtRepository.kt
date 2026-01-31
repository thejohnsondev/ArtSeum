package com.thejohnsondev.presentation.fakes

import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.ArtworkSearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeArtRepository : ArtRepository {

    private val _artworksFlow = MutableStateFlow<List<Artwork>>(emptyList())
    override val artworks: Flow<List<Artwork>> = _artworksFlow

    private var fetchArtworksResult: Result<Unit> = Result.success(Unit)
    private var searchArtworksResult: Result<List<ArtworkSearchItem>> = Result.success(emptyList())
    private var fetchArtworkDetailResult: Result<Artwork> = Result.failure(Exception("Not initialized"))
    
    // For verifying calls
    var fetchArtworksCalledWith: Pair<Int, Int>? = null
    var searchArtworksCalledWith: Triple<String, Int, Int>? = null

    fun emitArtworks(artworks: List<Artwork>) {
        _artworksFlow.value = artworks
    }

    fun setFetchArtworksResult(result: Result<Unit>) {
        fetchArtworksResult = result
    }

    fun setSearchArtworksResult(result: Result<List<ArtworkSearchItem>>) {
        searchArtworksResult = result
    }

    fun setFetchArtworkDetailResult(result: Result<Artwork>) {
        fetchArtworkDetailResult = result
    }

    override suspend fun fetchArtworks(page: Int, limit: Int): Result<Unit> {
        fetchArtworksCalledWith = page to limit
        return fetchArtworksResult
    }

    override suspend fun fetchArtworkById(artworkId: String): Result<Artwork> {
        return fetchArtworkDetailResult
    }

    override suspend fun searchArtworks(
        query: String,
        page: Int,
        limit: Int
    ): Result<List<ArtworkSearchItem>> {
        searchArtworksCalledWith = Triple(query, page, limit)
        return searchArtworksResult
    }
}