package com.thejohnsondev.network.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworkListResponse(
    @SerialName("pagination")
    val pagination: Pagination,
    @SerialName("data")
    val data: List<ArtworkData>,
    @SerialName("info")
    val info: Info,
    @SerialName("config")
    val config: Config
)

@Serializable
data class Pagination(
    @SerialName("total")
    val total: Int,
    @SerialName("limit")
    val limit: Int,
    @SerialName("offset")
    val offset: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("current_page")
    val currentPage: Int,
    @SerialName("next_url")
    val nextUrl: String? = null
)