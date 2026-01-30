package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.base.DisplayableMessageValue
import com.thejohnsondev.common.base.OneTimeEvent
import com.thejohnsondev.common.base.ScreenState
import com.thejohnsondev.common.base.toDisplayableMessage
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.usecase.GetArtworkDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

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
            is Action.DismissError -> dismissError()
        }
    }

    private fun dismissError() {
        _state.update { it.copy(error = null) }
    }

    private fun loadDetail(artworkId: Int) = launchLoading {
        withContext(Dispatchers.IO) {
            val result = getArtworkDetailUseCase(artworkId)
            withContext(Dispatchers.Main) {
                if (result.isSuccess) {
                    val artwork = result.getOrNull()
                    _state.update {
                        it.copy(
                            artwork = artwork,
                            selectedImageIndex = 0
                        )
                    }
                    showContent()
                } else {
                    val throwable = result.exceptionOrNull() ?: Exception("Unknown error")
                    _state.update { it.copy(error = throwable.toDisplayableMessage()) }
                    showContent()
                }
            }
        }
    }

    private fun handleBackClick() = launch {
        sendEvent(NavigateBack)
    }

    private fun updateImageIndex(index: Int) {
        _state.update { it.copy(selectedImageIndex = index) }
    }

    sealed class Action {
        data class LoadDetail(val artworkId: Int) : Action()
        data object BackClicked : Action()
        data class ImageSwiped(val index: Int) : Action()
        data object DismissError : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val artwork: Artwork? = null,
        val selectedImageIndex: Int = 0,
        val error: DisplayableMessageValue? = null
    ) {
        val showLocation: Boolean
            get() = artwork?.latitude != null && artwork.longitude != null

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
