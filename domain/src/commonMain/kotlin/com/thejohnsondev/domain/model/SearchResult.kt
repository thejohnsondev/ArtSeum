package com.thejohnsondev.domain.model

sealed class SearchResult {
    data object Idle : SearchResult()
    data object Empty : SearchResult()
    data object Loading : SearchResult()
    data object Error : SearchResult()
    data class Success(val data: List<ArtworkSearchItem>) : SearchResult()
}