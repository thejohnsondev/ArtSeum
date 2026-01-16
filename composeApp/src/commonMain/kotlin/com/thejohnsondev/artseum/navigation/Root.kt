package com.thejohnsondev.artseum.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.thejohnsondev.artseum.screens.details.ArtworkDetailsScreen
import com.thejohnsondev.artseum.screens.list.ArtworkListScreen
import com.thejohonsondev.ui.designsystem.ArtSeumTheme

@Composable
fun Root() {
    ArtSeumTheme(
        darkTheme = true
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screens.List
        ) {
            composableAnimated<Screens.List> {
                ArtworkListScreen(
                    goToDetails = { artworkId ->
                        navController.navigate(Screens.Details(artworkId))
                    }
                )
            }
            composableAnimated<Screens.Details> {
                val artworkId = it.toRoute<Screens.Details>().artworkId
                ArtworkDetailsScreen(
                    artworkId = artworkId,
                    goBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}