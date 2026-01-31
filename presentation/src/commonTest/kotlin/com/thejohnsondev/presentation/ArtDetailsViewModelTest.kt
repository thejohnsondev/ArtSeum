package com.thejohnsondev.presentation

import app.cash.turbine.test
import com.thejohnsondev.common.base.OneTimeEvent
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.usecase.GetArtworkDetailUseCase
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
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class ArtDetailsViewModelTest {

    private lateinit var repository: FakeArtRepository
    private lateinit var getArtworkDetailUseCase: GetArtworkDetailUseCase
    private lateinit var viewModel: ArtDetailsViewModel
    
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeArtRepository()
        getArtworkDetailUseCase = GetArtworkDetailUseCase(repository)
        viewModel = ArtDetailsViewModel(getArtworkDetailUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load detail success updates state`() = runTest(testDispatcher) {
        val artwork = Artwork.demo.copy(id = 123, title = "Masterpiece")
        repository.setFetchArtworkDetailResult(Result.success(artwork))
        
        viewModel.perform(ArtDetailsViewModel.Action.LoadDetail(123))
        advanceUntilIdle()
        
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(artwork, state.artwork)
            assertEquals("Masterpiece", state.artwork?.title)
            assertEquals(0, state.selectedImageIndex)
            assertNotNull(state.formattedDescription) // Assuming demo has description
        }
    }

    @Test
    fun `load detail failure shows error`() = runTest(testDispatcher) {
        val error = Exception("Not Found")
        repository.setFetchArtworkDetailResult(Result.failure(error))
        
        viewModel.perform(ArtDetailsViewModel.Action.LoadDetail(999))
        advanceUntilIdle()
        
        viewModel.state.test {
            val state = awaitItem()
            assertNotNull(state.error)
        }
    }

    @Test
    fun `back clicked sends navigation event`() = runTest(testDispatcher) {
        viewModel.perform(ArtDetailsViewModel.Action.BackClicked)
        advanceUntilIdle()
        
        viewModel.getEventFlow().test {
            val event = awaitItem()
            assertEquals(ArtDetailsViewModel.NavigateBack, event)
        }
    }

    @Test
    fun `image swiped updates index`() = runTest(testDispatcher) {
        viewModel.perform(ArtDetailsViewModel.Action.ImageSwiped(2))
        advanceUntilIdle()
        viewModel.state.test {
            val state = awaitItem()
            assertEquals(2, state.selectedImageIndex)
        }
    }
    
    @Test
    fun `dismiss error clears error`() = runTest(testDispatcher) {
        repository.setFetchArtworkDetailResult(Result.failure(Exception("Fail")))
        viewModel.perform(ArtDetailsViewModel.Action.LoadDetail(1))
        advanceUntilIdle()
        
        viewModel.perform(ArtDetailsViewModel.Action.DismissError)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertNull(state.error)
        }
    }
    
    @Test
    fun `derived properties are correct`() = runTest(testDispatcher) {
        val artwork = Artwork.demo.copy(
            isOnView = true,
            galleryTitle = "Gallery A",
            latitude = 10.0,
            longitude = 20.0
        )
        repository.setFetchArtworkDetailResult(Result.success(artwork))
        viewModel.perform(ArtDetailsViewModel.Action.LoadDetail(1))
        advanceUntilIdle()
        
        viewModel.state.test {
            val state = awaitItem()
            
            // Check statusBadge
            assertNotNull(state.statusBadge)
            assertEquals("Gallery A", state.statusBadge?.galleryTitle)
            assertEquals(true, state.statusBadge?.isOnView)
            
            // Check showLocation
            assertEquals(true, state.showLocation)
            
            // Check facts
            val facts = state.facts
            // Demo artwork has medium, dimensions, style, placeOfOrigin
            assertEquals(4, facts.size)
        }
    }
}