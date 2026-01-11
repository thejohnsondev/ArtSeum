package com.thejohnsondev.network.di

import com.thejohnsondev.network.api.ArtApi
import com.thejohnsondev.network.api.ArtApiImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val apiModule = module {
    singleOf(::ArtApiImpl) { bind<ArtApi>() }
}