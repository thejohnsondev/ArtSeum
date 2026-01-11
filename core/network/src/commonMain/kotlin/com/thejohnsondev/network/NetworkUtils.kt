package com.thejohnsondev.network

import io.ktor.http.ContentType
import io.ktor.http.HttpMessageBuilder
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.contentType

fun URLBuilder.defaultUrlConfig() {
    protocol = URLProtocol.HTTPS
    host = BASE_URL
}

fun HttpMessageBuilder.defaultRequestConfig(
    contentType: ContentType = ContentType.Application.Json
) {
    contentType(contentType)
}