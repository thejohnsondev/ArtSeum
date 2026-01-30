package com.thejohnsondev.common.base

import com.thejohnsondev.common.HttpError
import com.thejohnsondev.common.NoInternetConnectionError

sealed class DisplayableMessageValue {
    data object UnknownError : DisplayableMessageValue()
    data object NoInternetError : DisplayableMessageValue()
    data class StringValue(val value: String) : DisplayableMessageValue()
}

fun Throwable.toDisplayableMessage(): DisplayableMessageValue {
    return when (this) {
        is NoInternetConnectionError -> DisplayableMessageValue.NoInternetError
        is HttpError -> DisplayableMessageValue.StringValue(this.errorMessage)
        else -> DisplayableMessageValue.UnknownError
    }
}