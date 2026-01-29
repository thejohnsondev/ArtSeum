package com.thejohonsondev.ui.designsystem

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

class CustomColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val error: Color,
    val onError: Color,
    val warning: Color,
    val onWarning: Color,
    val errorContainer: Color,
    val success: Color,
    val surface: Color,
    val surfaceSecondary: Color,
    val surfaceTertiary: Color,
    val onSurface: Color,
    val background: Color,
    val onBackground: Color,
    val shadow: Color,
    val surfaceTeal: Color,
    val tipBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textPrimaryInverted: Color,
    val textSecondaryInverted: Color,
    val textTertiaryInverted: Color,
    val idColor1: Color,
    val idColor2: Color,
    val idColor3: Color,
    val idColor4: Color,
)

object Colors {
    private val lightColors = CustomColorScheme(
        primary = Color(0xFF895323),
        onPrimary = Color(0xFFFFFFFF),
        primaryContainer = Color(0xffe3f8fa),
        error = Color(0xFFFF3F5F),
        onError = Color(0xFFFFFFFF),
        errorContainer = Color(0xFFFFDEE3),
        warning = Color(0xFFec942c),
        onWarning = Color(0xFFFFFFFF),
        success = Color(0xFF01B59C),
        surface = Color(0xFFFFFFFF),
        surfaceSecondary = Color(0xFF595959),
        surfaceTertiary = Color(0xFF979797),
        onSurface = Color(0xFF979797),
        background = Color(0xFFEEEEEE),
        onBackground = Color(0xFF000000),
        shadow = Color(0xFF1F1F1F),
        surfaceTeal = Color(0xFFd6e4e6),
        tipBackground = Color(0xFFFFF5E2),
        textPrimary = Color(0xFF000000),
        textSecondary = Color(0xFF595959),
        textTertiary = Color(0xFF979797),
        textPrimaryInverted = Color(0xFFFFFFFF),
        textSecondaryInverted = Color(0xFFAFAFAF),
        textTertiaryInverted = Color(0xFFC0C0C0),
        idColor1 = Color(0xFFFDF0FE),
        idColor2 = Color(0xFFd7ebef),
        idColor3 = Color(0xFFEEEEEE),
        idColor4 = Color(0xFFCAD8F0),
    )
    private val darkColors = CustomColorScheme(
        primary = Color(0xFF895323),
        onPrimary = Color(0xFF111111),
        primaryContainer = Color(0xffe3f8fa),
        error = Color(0xFFFF3F5F),
        onError = Color(0xFFFFFFFF),
        warning = Color(0xFFec942c),
        onWarning = Color(0xFFFFFFFF),
        errorContainer = Color(0xFF382023),
        success = Color(0xFF01B59C),
        surface = Color(0xFF161616),
        surfaceSecondary = Color(0xFF2E2E2E),
        surfaceTertiary = Color(0xFF3A3A3A),
        onSurface = Color(0xFFE0E0E0),
        background = Color(0xFF0B0B0B),
        onBackground = Color(0xFFFFFFFF),
        shadow = Color(0xFF000000),
        surfaceTeal = Color(0xFF2A4245),
        tipBackground = Color(0xFF332B23),
        textPrimary = Color(0xFFFFFFFF),
        textSecondary = Color(0xFFAFAFAF),
        textTertiary = Color(0xFFC0C0C0),
        textPrimaryInverted = Color(0xFFFFFFFF),
        textSecondaryInverted = Color(0xFFAFAFAF),
        textTertiaryInverted = Color(0xFFC0C0C0),
        idColor1 = Color(0xFF2C252B),
        idColor2 = Color(0xFF303B49),
        idColor3 = Color(0xFF222222),
        idColor4 = Color(0xFF2C2F32),
    )

    lateinit var colorScheme: CustomColorScheme
    fun setIsDarkColorScheme(isDarkTheme: Boolean) {
        colorScheme = if (isDarkTheme) darkColors else lightColors
    }
}

// Material3 Light Mode Colors, NOT USED
val primaryLight = Color(0xff028599)
val onPrimaryLight = Color.White
val primaryContainerLight = Color(0xFF80CBC4)
val onPrimaryContainerLight = Color(0xFF00524D)
val inversePrimaryLight = Color(0xFF80CBC4)
val secondaryLight = Color(0xFFFFC107)
val onSecondaryLight = Color.Black
val secondaryContainerLight = Color(0xFFFFECB3)
val onSecondaryContainerLight = Color(0xFF422F00)
val tertiaryLight = Color(0xFF673AB7)
val onTertiaryLight = Color.White
val tertiaryContainerLight = Color(0xFFD1C4E9)
val onTertiaryContainerLight = Color(0xFF21005E)
val backgroundLight = Color(0xFFFAFAFA)
val onBackgroundLight = Color.Black
val surfaceLight = Color(0xFFFAFAFA)
val onSurfaceLight = Color.Black
val surfaceVariantLight = Color(0xFFEEEEEE)
val onSurfaceVariantLight = Color(0xFF49454F)
val surfaceTintLight = Color(0xFF008B8B)
val inverseSurfaceLight = Color(0xFF303033)
val inverseOnSurfaceLight = Color(0xFFF1EFF4)
val errorLight = Color(0xFFFF3F5F)
val onErrorLight = Color.White
val errorContainerLight = Color(0xFFFCD8DF)
val onErrorContainerLight = Color(0xFF410002)
val outlineLight = Color(0xFF79747E)
val outlineVariantLight = Color(0xFFCAC4D0)
val scrimLight = Color(0xFF000000)
val surfaceBrightLight = Color(0xFFFAFAFA)
val surfaceDimLight = Color(0xFFFAFAFA)
val surfaceContainerLight = Color.White
val surfaceContainerHighLight = Color.White
val surfaceContainerHighestLight = Color.White
val surfaceContainerLowLight = Color.White
val surfaceContainerLowestLight = Color.White

// Material3 Dark Mode Colors, NOT USED
val primaryDark = Color(0xFF80CBC4)
val onPrimaryDark = Color.Black
val primaryContainerDark = Color(0xFF00524D)
val onPrimaryContainerDark = Color(0xFF80CBC4)
val inversePrimaryDark = Color(0xFF008B8B)
val secondaryDark = Color(0xFFFFECB3)
val onSecondaryDark = Color.Black
val secondaryContainerDark = Color(0xFF422F00)
val onSecondaryContainerDark = Color(0xFFFFECB3)
val tertiaryDark = Color(0xFFD1C4E9)
val onTertiaryDark = Color.Black
val tertiaryContainerDark = Color(0xFF4A4458)
val onTertiaryContainerDark = Color(0xFFEBD9FF)
val backgroundDark = Color(0xFF121212)
val onBackgroundDark = Color.White
val surfaceDark = Color(0xFF121212)
val onSurfaceDark = Color.White
val surfaceVariantDark = Color(0xFF49454F)
val onSurfaceVariantDark = Color(0xFFCAC4D0)
val surfaceTintDark = Color(0xFF008B8B)
val inverseSurfaceDark = Color(0xFFE6E1E5)
val inverseOnSurfaceDark = Color(0xFF303033)
val errorDark = Color(0xFFF2B8B5)
val onErrorDark = Color.Black
val errorContainerDark = Color(0xFF8C1D18)
val onErrorContainerDark = Color(0xFFF9DEDC)
val outlineDark = Color(0xFF938F99)
val outlineVariantDark = Color(0xFF49454F)
val scrimDark = Color(0xFF000000)
val surfaceBrightDark = Color(0xFF121212)
val surfaceDimDark = Color(0xFF121212)
val surfaceContainerDark = Color.Black
val surfaceContainerHighDark = Color.Black
val surfaceContainerHighestDark = Color.Black
val surfaceContainerLowDark = Color.Black
val surfaceContainerLowestDark = Color.Black

val LightColorScheme = ColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    inversePrimary = inversePrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    surfaceTint = surfaceTintLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    surfaceBright = surfaceBrightLight,
    surfaceDim = surfaceDimLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
)

val DarkColorScheme = ColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    inversePrimary = inversePrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    surfaceTint = surfaceTintDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    surfaceBright = surfaceBrightDark,
    surfaceDim = surfaceDimDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
)