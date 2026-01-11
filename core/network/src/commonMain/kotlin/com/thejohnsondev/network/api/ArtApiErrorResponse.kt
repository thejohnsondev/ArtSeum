package com.thejohnsondev.network.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtApiErrorResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("error")
    val error: String,
    @SerialName("detail")
    val detail: String?
)