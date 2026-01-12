package com.thejohnsondev.data.di

import com.thejohnsondev.data.ArtRepositoryImpl
import com.thejohnsondev.domain.ArtRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    singleOf(::ArtRepositoryImpl) { bind<ArtRepository>() }
}