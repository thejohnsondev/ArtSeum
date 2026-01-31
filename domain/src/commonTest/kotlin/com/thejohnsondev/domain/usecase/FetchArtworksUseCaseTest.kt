package com.thejohnsondev.domain.usecase

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FetchArtworksUseCaseTest {

    private val fakeRepository = FakeArtRepository()
    private val fetchArtworksUseCase = FetchArtworksUseCase(fakeRepository)

    @Test
    fun `FetchArtworksUseCase calls repository with correct arguments`() = runTest {
        val result = fetchArtworksUseCase(page = 2, limit = 20)
        assertTrue(result.isSuccess)
        assertEquals(1, fakeRepository.fetchCallCount)
        assertEquals(2, fakeRepository.lastFetchPage)
        assertEquals(20, fakeRepository.lastFetchLimit)
    }

    @Test
    fun `FetchArtworksUseCase returns failure when repository fails`() = runTest {
        fakeRepository.shouldReturnError = true
        val result = fetchArtworksUseCase(page = 1)
        assertTrue(result.isFailure)
        assertEquals("Fetch Error", result.exceptionOrNull()?.message)
    }
}
