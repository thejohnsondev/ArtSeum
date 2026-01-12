package com.thejohnsondev.domain.di

import com.thejohnsondev.domain.usecase.FetchArtworksUseCase
import com.thejohnsondev.domain.usecase.GetArtworkDetailUseCase
import com.thejohnsondev.domain.usecase.ObserveArtworksUseCase
import com.thejohnsondev.domain.usecase.SearchArtworksUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        FetchArtworksUseCase(get())
    }
    factory {
        GetArtworkDetailUseCase(get())
    }
    factory {
        ObserveArtworksUseCase(get())
    }
    factory {
        SearchArtworksUseCase(get())
    }
}