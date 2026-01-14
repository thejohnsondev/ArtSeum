package com.thejohnsondev.artseum

import android.content.Context
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

actual class KoinInitializer(
    private val context: Context
) {
    actual fun init() {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(
                    allModules
                )
            }
        }
    }
}