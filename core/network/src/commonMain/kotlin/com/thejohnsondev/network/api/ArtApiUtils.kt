package com.thejohnsondev.network.api

import com.thejohnsondev.common.HttpError
import com.thejohnsondev.common.NoInternetConnectionError
import com.thejohnsondev.common.UnknownError
import com.thejohnsondev.network.api.models.ArtApiErrorResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMessageBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType

suspend inline fun <reified T> callWithMapping(call: (() -> HttpResponse)): Result<T> {
    return try {
        val response = call.invoke()
        return when (response.status.value) {
            in 200..300 -> {
                Result.success(response.body<T>())
            }

            in 400..600 -> {
                Result.failure(
                    HttpError(
                        response.status.value,
                        response.body<ArtApiErrorResponse>().error
                    )
                )
            }

            else -> {
                Result.failure(UnknownError())
            }
        }
    } catch (e: Exception) {
        if (e is NoInternetConnectionError) {
            Result.failure(e)
        } else {
            Result.failure(UnknownError())
        }
    }
}

fun URLBuilder.defaultUrlConfig() {
    protocol = URLProtocol.HTTPS
    host = ARTIC_BASE_URL
}

fun HttpMessageBuilder.defaultRequestConfig(
    contentType: ContentType = ContentType.Application.Json
) {
    contentType(contentType)
}