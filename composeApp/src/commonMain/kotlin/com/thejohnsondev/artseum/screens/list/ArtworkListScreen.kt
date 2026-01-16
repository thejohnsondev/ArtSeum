package com.thejohnsondev.artseum.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun ArtworkListScreen(
    goToDetails: (artworkId: String) -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text("Artwork List Screen")
    }
}