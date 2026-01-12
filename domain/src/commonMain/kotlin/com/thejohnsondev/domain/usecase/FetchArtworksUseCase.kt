package com.thejohnsondev.domain.usecase

import com.thejohnsondev.domain.ArtRepository

class FetchArtworksUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(page: Int, limit: Int = 10): Result<Unit> {
        return artRepository.fetchArtworks(page, limit)
    }
}