package com.thejohnsondev.artseum

import com.thejohnsondev.data.di.dataModule
import com.thejohnsondev.domain.di.domainModule
import com.thejohnsondev.presentation.di.presentationModule

expect class KoinInitializer {
    fun init()
}

val commonModules = listOf(
    dataModule,
    domainModule,
    presentationModule
)

val allModules = commonModules