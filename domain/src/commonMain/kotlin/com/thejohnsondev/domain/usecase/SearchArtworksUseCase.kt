package com.thejohnsondev.domain.usecase

import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.domain.model.ArtworkSearchItem
import com.thejohnsondev.domain.model.SearchResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.transformLatest

class SearchArtworksUseCase(
    private val artRepository: ArtRepository
) {

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    operator fun invoke(queryStream: Flow<String>): Flow<SearchResult> {
        return queryStream
            .debounce(750)
            .distinctUntilChanged()
            .transformLatest { query ->
                if (query.isBlank()) {
                    emit(SearchResult.Idle)
                    return@transformLatest
                }

                emit(SearchResult.Loading)

                try {
                    val result = artRepository.searchArtworks(query = query, page = 1, limit = 20)
                    if (result.isSuccess) {
                        emit(SearchResult.Success(result.getOrDefault(emptyList())))
                    } else {
                        emit(SearchResult.Error)
                    }
                } catch (e: Exception) {
                    emit(SearchResult.Error)
                }
            }
    }

    suspend operator fun invoke(
        query: String,
        page: Int,
        limit: Int
    ): Result<List<ArtworkSearchItem>> {
        if (query.isBlank()) return Result.success(emptyList())
        return artRepository.searchArtworks(query = query, page = page, limit = limit)
    }
}