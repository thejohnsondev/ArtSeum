package com.thejohnsondev.common

open class Error() : Exception()

data class HttpError(
    val code: Int,
    val errorMessage: String
) : Error()

class NoInternetConnectionError : Exception()
class UnknownError : Error()