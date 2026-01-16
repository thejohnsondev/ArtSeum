package com.thejohnsondev.artseum.navigation

import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    object List : Screens()
    @Serializable
    data class Details(val artworkId: String) : Screens()
}