package com.thejohnsondev.common

import kotlin.jvm.Transient

class NoInternetConnectionException : Throwable()

open class Error(
    @Transient val throwable: Throwable? = null
)

data class HttpError(
    val code: Int,
    val message: String,
) : Error()

data class NetworkError(val cause: Throwable) : Error(cause)

data class UnknownError(val cause: Throwable) : Error(cause)
data object InvalidTokenError : Error()
data object LoginAgainError : Error()