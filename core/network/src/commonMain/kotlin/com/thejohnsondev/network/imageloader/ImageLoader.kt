package com.thejohnsondev.network.imageloader

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.thejohnsondev.network.api.ARTIC_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.header

fun getAsyncImageLoader(context: PlatformContext): ImageLoader {
    val httpClient = HttpClient {
        install(DefaultRequest) {
            header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Mobile/15E148 Safari/604.1")
            header("Referer", ARTIC_BASE_URL)
        }
    }

    return ImageLoader.Builder(context)
        .components {
            add(KtorNetworkFetcherFactory(httpClient))
        }
        .build()
}