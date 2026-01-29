package com.thejohnsondev.artseum.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.compose.setSingletonImageLoaderFactory
import com.thejohnsondev.artseum.screens.details.ArtworkDetailsScreen
import com.thejohnsondev.artseum.screens.list.ArtListScreen
import com.thejohnsondev.common.Logger
import com.thejohnsondev.network.imageloader.getAsyncImageLoader
import com.thejohonsondev.ui.designsystem.ArtSeumTheme
import com.thejohonsondev.ui.designsystem.Colors

@Composable
fun Root() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    LaunchedEffect(Unit) {
        initLibs()
    }

    ArtSeumTheme(
        darkTheme = true
    ) {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Colors.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = Screens.List
            ) {
                composableAnimated<Screens.List> {
                    ArtListScreen(
                        goToDetails = { artworkId ->
                            navController.navigate(Screens.Details(artworkId))
                        }
                    )
                }
                composableAnimated<Screens.Details> {
                    val artworkId = it.toRoute<Screens.Details>().artworkId
                    ArtworkDetailsScreen(
                        artworkId = artworkId,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

private fun initLibs() {
    initLogger()
}

private fun initLogger() {
    Logger.initialize()
}