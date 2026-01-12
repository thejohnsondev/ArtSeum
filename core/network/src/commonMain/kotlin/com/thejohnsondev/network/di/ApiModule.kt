package com.thejohnsondev.network.di

import com.thejohnsondev.network.api.ArtApiService
import com.thejohnsondev.network.api.ArtApiServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val apiModule = module {
    singleOf(::ArtApiServiceImpl) { bind<ArtApiService>() }
}