package com.thejohnsondev.domain.usecase

import app.cash.turbine.test
import com.thejohnsondev.domain.model.SearchResult
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchArtworksUseCaseTest {

    private val fakeRepository = FakeArtRepository()
    private val searchArtworksUseCase = SearchArtworksUseCase(fakeRepository)

    @Test
    fun `SearchArtworksUseCase flow emits Idle for blank query`() = runTest {
        val queryFlow = flowOf("")
        
        searchArtworksUseCase(queryFlow).test {
            assertEquals(SearchResult.Idle, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `SearchArtworksUseCase flow emits Loading then Success`() = runTest {
        val queryFlow = flowOf("Monet")
        
        searchArtworksUseCase(queryFlow).test {
            // Wait for Loading (emitted immediately after debounce logic processes)
            assertEquals(SearchResult.Loading, awaitItem())
            
            val successItem = awaitItem()
            assertTrue(successItem is SearchResult.Success)
            assertEquals(1, successItem.data.size)
            
            awaitComplete()
        }
    }

    @Test
    fun `SearchArtworksUseCase flow emits Error when repository fails`() = runTest {
        val queryFlow = flowOf("error")
        
        searchArtworksUseCase(queryFlow).test {
            assertEquals(SearchResult.Loading, awaitItem())
            assertEquals(SearchResult.Error, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `SearchArtworksUseCase flow emits Success with empty list when no results`() = runTest {
        val queryFlow = flowOf("empty")
        
        searchArtworksUseCase(queryFlow).test {
            assertEquals(SearchResult.Loading, awaitItem())
            val result = awaitItem()
            assertTrue(result is SearchResult.Success)
            assertTrue(result.data.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `SearchArtworksUseCase direct call returns result`() = runTest {
        val result = searchArtworksUseCase("Monet", 1, 10)
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.size)
    }
}
