package com.thejohnsondev.artseum

import com.thejohnsondev.data.di.dataModule
import com.thejohnsondev.domain.di.domainModule
import com.thejohnsondev.network.di.apiModule
import com.thejohnsondev.network.di.networkModule
import com.thejohnsondev.presentation.di.presentationModule

expect class KoinInitializer {
    fun init()
}

val commonModules = listOf(
    networkModule,
    apiModule,
    dataModule,
    domainModule,
    presentationModule
)

val allModules = commonModules