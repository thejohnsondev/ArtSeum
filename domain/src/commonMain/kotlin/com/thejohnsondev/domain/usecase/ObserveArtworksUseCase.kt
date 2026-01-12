package com.thejohnsondev.domain.usecase

import com.thejohnsondev.domain.ArtRepository
import com.thejohnsondev.domain.model.Artwork
import kotlinx.coroutines.flow.Flow

class ObserveArtworksUseCase(
    private val artRepository: ArtRepository
) {
    operator fun invoke(): Flow<List<Artwork>> {
        return artRepository.artworks
    }
}