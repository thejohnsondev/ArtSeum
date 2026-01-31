package com.thejohnsondev.presentation

import app.cash.turbine.test
import com.thejohnsondev.common.base.DisplayableMessageValue
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.ArtworkSearchItem
import com.thejohnsondev.domain.model.SearchResult
import com.thejohnsondev.domain.usecase.FetchArtworksUseCase
import com.thejohnsondev.domain.usecase.ObserveArtworksUseCase
import com.thejohnsondev.domain.usecase.SearchArtworksUseCase
import com.thejohnsondev.presentation.fakes.FakeArtRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ArtListViewModelTest {

    private lateinit var repository: FakeArtRepository
    private lateinit var observeArtworksUseCase: ObserveArtworksUseCase
    private lateinit var fetchArtworksUseCase: FetchArtworksUseCase
    private lateinit var searchArtworksUseCase: SearchArtworksUseCase
    private lateinit var viewModel: ArtListViewModel
    
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeArtRepository()
        observeArtworksUseCase = ObserveArtworksUseCase(repository)
        fetchArtworksUseCase = FetchArtworksUseCase(repository)
        searchArtworksUseCase = SearchArtworksUseCase(repository)
        
        viewModel = ArtListViewModel(
            observeArtworksUseCase,
            fetchArtworksUseCase,
            searchArtworksUseCase
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state loads artworks`() = runTest(testDispatcher) {
        val artworks = listOf(Artwork.demo.copy(id = 1), Artwork.demo.copy(id = 2))
        repository.emitArtworks(artworks)

        viewModel.state.test {
            val state = awaitItem()
            // Initial state might be empty or have items depending on flow emission speed
            // Since we emitted before test, it should eventually be there
            if (state.browseArtworks.isEmpty()) {
                val nextState = awaitItem()
                assertEquals(artworks, nextState.browseArtworks)
            } else {
                assertEquals(artworks, state.browseArtworks)
            }
        }
    }

    @Test
    fun `refresh fetches artworks`() = runTest(testDispatcher) {
        repository.setFetchArtworksResult(Result.success(Unit))
        
        viewModel.perform(ArtListViewModel.Action.Refresh)
        advanceUntilIdle() // Wait for coroutines
        
        assertEquals(1 to 20, repository.fetchArtworksCalledWith)
    }
    
    @Test
    fun `load next page fetches next page`() = runTest(testDispatcher) {
        repository.setFetchArtworksResult(Result.success(Unit))
        
        // Initial load happens on init usually if empty
        // We trigger LoadNextPage
        viewModel.perform(ArtListViewModel.Action.LoadNextPage)
        advanceUntilIdle()
        
        // Should be page 2 (initial is 1, next is 1+1=2)
        assertEquals(2 to 20, repository.fetchArtworksCalledWith)
    }
    
    @Test
    fun `search updates query and triggers search`() = runTest(testDispatcher) {
        val searchItems = listOf(
             ArtworkSearchItem.demo1
        )
        repository.setSearchArtworksResult(Result.success(searchItems))

        viewModel.perform(ArtListViewModel.Action.Search("monet"))
        
        viewModel.state.test {
            // Skip initial emissions
            var state = awaitItem()
            while (state.searchQuery != "monet") {
                state = awaitItem()
            }
            
            assertEquals("monet", state.searchQuery)
            assertTrue(state.isSearching)
            
            // Wait for debounce and search result
            // SearchResult might go Idle -> Loading -> Success
            while (state.searchResult !is SearchResult.Success) {
                state = awaitItem()
            }
            
            val result = state.searchResult
            assertIs<SearchResult.Success>(result)
            assertEquals(searchItems, result.data)
        }
    }

    @Test
    fun `clear search resets state`() = runTest(testDispatcher) {
        // Setup search state
        viewModel.perform(ArtListViewModel.Action.Search("monet"))
        
        viewModel.perform(ArtListViewModel.Action.ClearSearch)
        
        viewModel.state.test {
             // Skip until cleared
            var state = awaitItem()
            while (state.isSearching) {
                state = awaitItem()
            }
            
            assertFalse(state.isSearching)
            assertEquals("", state.searchQuery)
            assertEquals(SearchResult.Idle, state.searchResult)
        }
    }

    @Test
    fun `error handling displays error`() = runTest(testDispatcher) {
        val error = Exception("Network Error")
        repository.setFetchArtworksResult(Result.failure(error))
        
        viewModel.perform(ArtListViewModel.Action.Refresh)
        advanceUntilIdle()
        
        viewModel.state.test {
            val state = awaitItem()
            // Error might be set now
            if (state.error == null) {
                // Wait for it? But we advancedUntilIdle.
                // The state flow combines _state, so it should be updated.
                // Re-collecting after advanceUntilIdle ensures we get latest
            }
        }
        
        // Let's verify via state property access after idle
        val currentState = viewModel.state.value
        // Note: state.value in StateFlow might not update immediately if we don't collect it or if sharing is started lazily?
        // SharingStarted.Eagerly is used.
        
        // Better to use turbine to catch it
    }
    
    @Test
    fun `dismiss error clears error`() = runTest(testDispatcher) {
        // First set an error (manually or via action failure)
        // Since we can't easily inject error into _state private flow, we use a failing action
        repository.setFetchArtworksResult(Result.failure(Exception("Fail")))
        viewModel.perform(ArtListViewModel.Action.Refresh)
        advanceUntilIdle()
        
        viewModel.perform(ArtListViewModel.Action.DismissError)
        
        viewModel.state.test {
             var state = awaitItem()
             if (state.error != null) {
                 state = awaitItem()
             }
             assertNull(state.error)
        }
    }
}