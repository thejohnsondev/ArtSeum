package com.thejohnsondev.presentation.di

import com.thejohnsondev.presentation.ArtDetailsViewModel
import com.thejohnsondev.presentation.ArtListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::ArtListViewModel)
    viewModelOf(::ArtDetailsViewModel)
}