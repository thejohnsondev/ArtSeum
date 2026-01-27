package com.thejohnsondev.network.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtSearchResponse(
    @SerialName("pagination")
    val pagination: Pagination,
    @SerialName("data")
    val data: List<ArtworkSearchData>,
    @SerialName("info")
    val info: Info,
    @SerialName("config")
    val config: Config
)

@Serializable
data class ArtworkSearchData(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("api_model")
    val apiModel: String? = null,
    @SerialName("api_link")
    val apiLink: String? = null,
    @SerialName("is_boosted")
    val isBoosted: Boolean? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail?? = null,
    @SerialName("timestamp")
    val timestamp: String? = null
)