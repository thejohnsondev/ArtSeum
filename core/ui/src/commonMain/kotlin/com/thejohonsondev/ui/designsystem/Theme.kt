package com.thejohonsondev.ui.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

private var isDarkColorScheme = false

@Composable
fun ArtSeumTheme(
    darkTheme: Boolean = false, // to use device theme use isSystemInDarkTheme()
    useCustomTypography: Boolean = true,
    content: @Composable () -> Unit,
) {
    isDarkColorScheme = darkTheme
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    Colors.setIsDarkColorScheme(darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = if (useCustomTypography) getCustomTypography() else getDefaultTypography(),
        content = content
    )
}

@Composable
fun isDarkColorScheme(): Boolean {
    return isDarkColorScheme
}

@Composable
fun PreviewTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    ArtSeumTheme(
        darkTheme = darkTheme,
        useCustomTypography = true,
        content = content
    )
}