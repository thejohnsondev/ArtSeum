package com.thejohnsondev.domain.usecase

import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.domain.model.Artwork

class GetArtworkDetailUseCase(
    private val artRepository: ArtRepository
) {
    suspend operator fun invoke(artworkId: String): Result<Artwork> {
        return artRepository.fetchArtworkById(artworkId)
    }
}