package com.thejohnsondev.domain.usecase

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ObserveArtworksUseCaseTest {

    private val fakeRepository = FakeArtRepository()
    private val observeArtworksUseCase = ObserveArtworksUseCase(fakeRepository)

    @Test
    fun `ObserveArtworksUseCase returns flow from repository`() = runTest {
        fakeRepository.emitArtworks(listOf(testArtwork))
        
        observeArtworksUseCase().test {
            val items = awaitItem()
            assertEquals(1, items.size)
            assertEquals(testArtwork.id, items.first().id)
        }
    }

    @Test
    fun `ObserveArtworksUseCase emits empty list when repository is empty`() = runTest {
        fakeRepository.emitArtworks(emptyList())
        
        observeArtworksUseCase().test {
            val items = awaitItem()
            assertEquals(0, items.size)
        }
    }
}
