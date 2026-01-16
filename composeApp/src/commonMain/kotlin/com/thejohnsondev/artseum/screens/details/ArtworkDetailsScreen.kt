package com.thejohnsondev.artseum.screens.details

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ArtworkDetailsScreen(
    artworkId: String,
    goBack: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text("Artwork Details Screen: $artworkId")
    }
}