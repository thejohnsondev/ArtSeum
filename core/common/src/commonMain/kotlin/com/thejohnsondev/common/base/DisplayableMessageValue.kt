package com.thejohnsondev.common.base

sealed interface DisplayableMessageValue {
    data object UnknownError : DisplayableMessageValue
    data class StringValue(val value: String) : DisplayableMessageValue
}