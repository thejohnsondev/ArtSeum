package com.thejohonsondev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun FadingBox(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.background
) {
    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        color,
                        color.copy(alpha = 0f),
                    )
                )
            )
    )
}