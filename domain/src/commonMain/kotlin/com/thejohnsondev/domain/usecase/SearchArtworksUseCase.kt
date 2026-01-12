package com.thejohnsondev.domain.usecase

import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.domain.model.Artwork

class SearchArtworksUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(query: String): Result<List<Artwork>> {
        if (query.isBlank()) return Result.success(emptyList())
        return artRepository.searchArtworks(query)
    }
}