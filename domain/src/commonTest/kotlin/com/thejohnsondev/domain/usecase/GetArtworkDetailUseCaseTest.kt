package com.thejohnsondev.domain.usecase

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetArtworkDetailUseCaseTest {

    private val fakeRepository = FakeArtRepository()
    private val getArtworkDetailUseCase = GetArtworkDetailUseCase(fakeRepository)

    @Test
    fun `GetArtworkDetailUseCase returns artwork`() = runTest {
        val result = getArtworkDetailUseCase(123)
        assertTrue(result.isSuccess)
        assertEquals(123, result.getOrNull()?.id)
    }

    @Test
    fun `GetArtworkDetailUseCase returns failure when repository fails`() = runTest {
        fakeRepository.shouldReturnError = true
        val result = getArtworkDetailUseCase(123)
        assertTrue(result.isFailure)
        assertEquals("Detail Error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `GetArtworkDetailUseCase returns failure for invalid id`() = runTest {
        val result = getArtworkDetailUseCase(-1)
        assertTrue(result.isFailure)
        // Fake repo treats -1 as error trigger specific to ID
        assertEquals("Detail Error", result.exceptionOrNull()?.message)
    }
}
