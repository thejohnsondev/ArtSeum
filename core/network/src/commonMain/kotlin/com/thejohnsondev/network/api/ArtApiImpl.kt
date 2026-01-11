package com.thejohnsondev.network.api

import com.thejohnsondev.network.api.models.ArtResponse
import com.thejohnsondev.network.api.models.ArtworkListResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.path

class ArtApiImpl(
    private val client: HttpClient
): ArtApi {

    override suspend fun fetchArtworks(
        page: Int,
        limit: Int,
    ): Result<ArtworkListResponse> = callWithMapping {
        client.get {
            defaultRequestConfig()
            url {
                defaultUrlConfig()
                path(URL_ARTWORKS)
                parameters.append("page", page.toString())
                parameters.append("limit", limit.toString())
            }
        }
    }

    override suspend fun fetchArtworkById(artworkId: String): Result<ArtResponse> = callWithMapping {
        client.get {
            defaultRequestConfig()
            url {
                defaultUrlConfig()
                path(URL_ARTWORKS, artworkId)
            }
        }
    }

    override suspend fun searchArtworks(
        query: String
    ): Result<ArtworkListResponse> = callWithMapping {
        client.get {
            defaultRequestConfig()
            url {
                defaultUrlConfig()
                path(URL_ARTWORKS_SEARCH)
                parameters.append("q", query)
            }
        }
    }

}