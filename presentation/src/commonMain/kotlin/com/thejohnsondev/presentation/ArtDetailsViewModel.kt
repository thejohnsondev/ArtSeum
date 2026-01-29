package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.base.OneTimeEvent
import com.thejohnsondev.common.base.ScreenState
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.usecase.GetArtworkDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class ArtDetailsViewModel(
    private val getArtworkDetailUseCase: GetArtworkDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(State())
    val state = combine(
        screenState,
        _state
    ) { screenState, state ->
        state.copy(screenState = screenState)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, State())

    fun perform(action: Action) {
        when (action) {
            is Action.LoadDetail -> loadDetail(action.artworkId)
            is Action.BackClicked -> handleBackClick()
            is Action.ImageSwiped -> updateImageIndex(action.index)
        }
    }

    private fun loadDetail(artworkId: String) = launchLoading {
        val result = getArtworkDetailUseCase(artworkId)
        if (result.isSuccess) {
            val artwork = result.getOrNull()
            _state.update {
                it.copy(
                    artwork = artwork,
                    // Reset image index on new load
                    selectedImageIndex = 0
                )
            }
            showContent()
        } else {
            showError("Failed to load artwork details")
        }
    }

    private fun handleBackClick() = launch {
        sendEvent(NavigateBack)
    }

    private fun updateImageIndex(index: Int) {
        _state.update { it.copy(selectedImageIndex = index) }
    }

    sealed interface Action {
        data class LoadDetail(val artworkId: String) : Action
        data object BackClicked : Action
        data class ImageSwiped(val index: Int) : Action
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val artwork: Artwork? = null,
        val selectedImageIndex: Int = 0
    ) {
        // Helpers for UI logic
        val showLocation: Boolean
            get() = artwork?.latitude != null && artwork?.longitude != null
        
        val statusBadgeText: String?
            get() = if (artwork?.isOnView == true) {
                if (!artwork.galleryTitle.isNullOrBlank()) {
                    "On View - ${artwork.galleryTitle}"
                } else {
                    "On View"
                }
            } else {
                null
            }
            
        val facts: List<Pair<String, String>>
            get() = artwork?.let { art ->
                listOfNotNull(
                    "Medium" to art.medium,
                    "Dimensions" to art.dimensions,
                    art.style?.let { "Style" to it },
                    "Place" to art.placeOfOrigin
                )
            } ?: emptyList()
    }
    
    data object NavigateBack : OneTimeEvent()
}
