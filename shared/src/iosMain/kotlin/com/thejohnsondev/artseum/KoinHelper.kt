package com.thejohnsondev.artseum

import com.thejohnsondev.presentation.ArtListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun doInitKoin() {
    KoinInitializer().start()
}

class ViewModelFactory : KoinComponent {
    fun makeArtListViewModel(): ArtListViewModel = get()
}