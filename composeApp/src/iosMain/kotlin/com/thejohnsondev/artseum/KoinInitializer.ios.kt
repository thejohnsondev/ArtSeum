package com.thejohnsondev.artseum

import com.thejohnsondev.presentation.ArtListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(
                allModules
            )
        }
    }
}

class ViewModelFactory : KoinComponent {
    fun makeArtListViewModel(): ArtListViewModel = get()
}