package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.base.DisplayableMessageValue
import com.thejohnsondev.common.base.OneTimeEvent
import com.thejohnsondev.common.base.ScreenState
import com.thejohnsondev.common.base.toDisplayableMessage
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
            is Action.DismissError -> dismissError()
        }
    }

    private fun dismissError() {
        _state.update { it.copy(error = null) }
    }

    private fun loadDetail(artworkId: Int) = launchLoading {
        val result = getArtworkDetailUseCase(artworkId)
        if (result.isSuccess) {
            val artwork = result.getOrNull()
            _state.update {
                it.copy(
                    artwork = artwork,
                    formattedDescription = artwork?.description?.let(::stripHtml),
                    formattedHistory = artwork?.exhibitionHistory?.let(::stripHtml),
                    formattedPublicationHistory = artwork?.publicationHistory?.let(::stripHtml),
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

    private fun handleBackClick() = launch {
        sendEvent(NavigateBack)
    }

    private fun updateImageIndex(index: Int) {
        _state.update { it.copy(selectedImageIndex = index) }
    }

    private fun stripHtml(html: String): String {
        return html.replace(Regex("<.*?>"), "")
    }

    sealed class Action {
        data class LoadDetail(val artworkId: Int) : Action()
        data object BackClicked : Action()
        data class ImageSwiped(val index: Int) : Action()
        data object DismissError : Action()
    }

    enum class FactType {
        Medium, Dimensions, Style, Place
    }

    data class StatusBadge(
        val isOnView: Boolean,
        val galleryTitle: String? = null
    )

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val artwork: Artwork? = null,
        val formattedDescription: String? = null,
        val formattedHistory: String? = null,
        val formattedPublicationHistory: String? = null,
        val selectedImageIndex: Int = 0,
        val error: DisplayableMessageValue? = null
    ) {
        val showLocation: Boolean
            get() = artwork?.latitude != null && artwork.longitude != null

        val statusBadge: StatusBadge?
            get() = if (artwork?.isOnView == true) {
                StatusBadge(isOnView = true, galleryTitle = artwork.galleryTitle)
            } else {
                null
            }

        val facts: List<Pair<FactType, String>>
            get() = artwork?.let { art ->
                listOfNotNull(
                    FactType.Medium to art.medium,
                    FactType.Dimensions to art.dimensions,
                    art.style?.let { FactType.Style to it },
                    FactType.Place to art.placeOfOrigin
                )
            } ?: emptyList()
    }

    data object NavigateBack : OneTimeEvent()
}
