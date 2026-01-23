package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.base.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.usecase.ObserveArtworksUseCase
import com.thejohnsondev.domain.usecase.FetchArtworksUseCase
import com.thejohnsondev.domain.usecase.SearchArtworksUseCase

class ArtListViewModel(
    private val getArtworksStreamUseCase: ObserveArtworksUseCase,
    private val fetchArtworksUseCase: FetchArtworksUseCase,
    private val searchArtworksUseCase: SearchArtworksUseCase
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
            is Action.LoadData -> loadData()
            is Action.Refresh -> refresh()
            is Action.LoadNextPage -> loadNextPage()
            is Action.Search -> search(action.query)
            is Action.ClearSearch -> clearSearch()
        }
    }

    private fun loadData() = launch {
        getArtworksStreamUseCase().collect { artworks ->
            _state.update {
                it.copy(browseArtworks = artworks)
            }

            if (artworks.isEmpty() && !_state.value.isSearching) {
                fetchArtworks(page = 1)
            }
        }
    }

    private fun refresh() {
        if (_state.value.isSearching) return

        _state.update { it.copy(currentPage = 1) }
        fetchArtworks(page = 1)
    }

    private fun loadNextPage() {
        val currentState = _state.value

        if (currentState.isSearching) {
            return
        } else {
            val nextPage = currentState.currentPage + 1
            fetchArtworks(page = nextPage)
        }
    }

    private fun fetchArtworks(page: Int) = launchLoading {
        val result = fetchArtworksUseCase(page = page, limit = 20)

        if (result.isSuccess) {
            _state.update { it.copy(currentPage = page) }
            showContent()
        } else {
            showContent()
        }
    }

    private fun search(query: String) {
        if (query.isBlank()) {
            clearSearch()
            return
        }

        _state.update { it.copy(searchQuery = query, isSearching = true) }

        launchLoading {
            val result = searchArtworksUseCase(query)

            if (result.isSuccess) {
                _state.update {
                    it.copy(searchResults = result.getOrNull() ?: emptyList())
                }
                showContent()
            } else {
                showContent()
            }
        }
    }

    private fun clearSearch() = launch {
        _state.update {
            it.copy(
                isSearching = false,
                searchQuery = "",
                searchResults = emptyList()
            )
        }
        showContent()
    }

    sealed class Action {
        data object LoadData : Action()
        data object Refresh : Action()
        data object LoadNextPage : Action()
        data class Search(val query: String) : Action()
        data object ClearSearch : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val browseArtworks: List<Artwork> = emptyList(),
        val searchResults: List<Artwork> = emptyList(),
        val isSearching: Boolean = false,
        val searchQuery: String = "",
        val currentPage: Int = 1
    ) {
        val displayedArtworks: List<Artwork>
            get() = if (isSearching) searchResults else browseArtworks
    }
}