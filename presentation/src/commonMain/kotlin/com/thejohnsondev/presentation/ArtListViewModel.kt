package com.thejohnsondev.presentation

import androidx.lifecycle.viewModelScope
import com.thejohnsondev.common.base.BaseViewModel
import com.thejohnsondev.common.base.DisplayableMessageValue
import com.thejohnsondev.common.base.ScreenState
import com.thejohnsondev.common.base.toDisplayableMessage
import com.thejohnsondev.domain.model.Artwork
import com.thejohnsondev.domain.model.SearchResult
import com.thejohnsondev.domain.usecase.FetchArtworksUseCase
import com.thejohnsondev.domain.usecase.ObserveArtworksUseCase
import com.thejohnsondev.domain.usecase.SearchArtworksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

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

    private val _searchQueryFlow = MutableStateFlow("")

    init {
        observeArtworks()
        observeSearchResults()
    }

    fun perform(action: Action) {
        when (action) {
            is Action.Refresh -> refresh()
            is Action.LoadNextPage -> loadNextPage()
            is Action.Search -> search(action.query)
            is Action.ClearSearch -> clearSearch()
            is Action.DismissError -> dismissError()
        }
    }

    private fun dismissError() {
        _state.update { it.copy(error = null) }
    }

    private fun observeArtworks() = launch {
        getArtworksStreamUseCase().collect { artworks ->
            _state.update {
                it.copy(browseArtworks = artworks)
            }

            if (artworks.isEmpty() && !_state.value.isSearching) {
                fetchArtworks(page = 1)
            }
        }
    }

    private fun observeSearchResults() = launch {
        searchArtworksUseCase(_searchQueryFlow)
            .collect { result ->
                handleSearchResult(result)
            }
    }

    private fun search(query: String) {
        _state.update { it.copy(searchQuery = query, isSearching = true) }
        _searchQueryFlow.value = query
    }

    private fun handleSearchResult(result: SearchResult) {
        _state.update {
            it.copy(
                searchResult = result,
                searchPage = 1
            )
        }
    }

    private fun loadNextPage() {
        val currentState = _state.value
        if (currentState.isSearching) {
            fetchSearchResultsNextPage(
                query = currentState.searchQuery,
                page = currentState.searchPage + 1
            )
        } else {
            fetchArtworks(page = currentState.currentPage + 1)
        }
    }

    private fun fetchSearchResultsNextPage(query: String, page: Int) = launchLoading {
        val result = searchArtworksUseCase(query = query, page = page, limit = 20)

        if (result.isSuccess) {
            val newItems = result.getOrNull() ?: emptyList()
            _state.update { state ->
                val currentList = (state.searchResult as? SearchResult.Success)?.data ?: emptyList()
                state.copy(
                    searchResult = SearchResult.Success(currentList + newItems),
                    searchPage = page
                )
            }
            showContent()
        } else {
            handleError(result.exceptionOrNull())
        }
    }

    private fun clearSearch() = launch {
        _state.update {
            it.copy(
                isSearching = false,
                searchQuery = "",
                searchResult = SearchResult.Idle,
                searchPage = 1
            )
        }
        _searchQueryFlow.value = ""
        showContent()
    }

    private fun refresh() {
        if (_state.value.isSearching) {
            val query = _state.value.searchQuery
            if (query.isNotBlank()) {
                search(query)
            }
        } else {
            _state.update { it.copy(currentPage = 1) }
            fetchArtworks(page = 1)
        }
    }

    private fun fetchArtworks(page: Int) = launchLoading {
        val result = fetchArtworksUseCase(page = page, limit = 20)

        if (result.isSuccess) {
            _state.update { it.copy(currentPage = page) }
            showContent()
        } else {
            handleError(result.exceptionOrNull())
        }
    }

    private fun handleError(throwable: Throwable?) = launch {
        val error = throwable ?: Exception("Unknown error")
        _state.update { it.copy(error = error.toDisplayableMessage()) }
        showContent()
    }

    sealed class Action {
        data object Refresh : Action()
        data object LoadNextPage : Action()
        data class Search(val query: String) : Action()
        data object ClearSearch : Action()
        data object DismissError : Action()
    }

    data class State(
        val screenState: ScreenState = ScreenState.None,
        val browseArtworks: List<Artwork> = emptyList(),
        val searchResult: SearchResult = SearchResult.Idle,
        val isSearching: Boolean = false,
        val searchQuery: String = "",
        val currentPage: Int = 1,
        val searchPage: Int = 1,
        val error: DisplayableMessageValue? = null
    )
}