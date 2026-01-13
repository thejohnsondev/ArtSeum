package com.thejohnsondev.common.base

sealed interface LoadingState {
    data object Loading : LoadingState
    data object Loaded : LoadingState
}