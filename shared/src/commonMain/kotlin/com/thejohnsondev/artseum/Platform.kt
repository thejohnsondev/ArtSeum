package com.thejohnsondev.artseum

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform