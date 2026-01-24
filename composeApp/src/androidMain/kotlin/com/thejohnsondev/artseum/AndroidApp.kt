package com.thejohnsondev.artseum

import android.app.Application

class AndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        KoinInitializer(
            context = applicationContext,
        ).start()
    }

}