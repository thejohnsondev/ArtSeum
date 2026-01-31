package com.thejohnsondev.domain.usecase

import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.ArtworkSearchItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

val testArtwork = Artwork.demo.copy(id = 123)
val testSearchItem = ArtworkSearchItem.demo1.copy(id = 123)

class FakeArtRepository : ArtRepository {

    private val _artworks = MutableStateFlow<List<Artwork>>(emptyList())
    override val artworks: Flow<List<Artwork>> = _artworks

    var fetchCallCount = 0
    var lastFetchPage: Int? = null
    var lastFetchLimit: Int? = null
    var shouldReturnError = false

    fun emitArtworks(list: List<Artwork>) {
        _artworks.value = list
    }

    override suspend fun fetchArtworks(page: Int, limit: Int): Result<Unit> {
        fetchCallCount++
        lastFetchPage = page
        lastFetchLimit = limit
        if (shouldReturnError) return Result.failure(Exception("Fetch Error"))
        return Result.success(Unit)
    }

    override suspend fun fetchArtworkById(artworkId: String): Result<Artwork> {
        if (shouldReturnError || artworkId == "-1") return Result.failure(Exception("Detail Error"))
        return Result.success(testArtwork.copy(id = artworkId.toInt()))
    }

    override suspend fun searchArtworks(
        query: String,
        page: Int,
        limit: Int
    ): Result<List<ArtworkSearchItem>> {
        if (shouldReturnError || query == "error") return Result.failure(Exception("Search Error"))
        if (query == "empty") return Result.success(emptyList())
        return Result.success(listOf(testSearchItem))
    }
}
