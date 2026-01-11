package com.thejohnsondev.common

expect fun getPlatform(): Platform

enum class Platform {
    ANDROID,
    IOS_DEVICE,
    IOS_SIMULATOR,
}