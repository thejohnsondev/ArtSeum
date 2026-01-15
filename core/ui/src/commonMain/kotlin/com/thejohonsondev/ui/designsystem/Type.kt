package com.thejohonsondev.ui.designsystem

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thejohnsondev.ui.generated.resources.Res
import com.thejohnsondev.ui.generated.resources.poppins_black
import com.thejohnsondev.ui.generated.resources.poppins_bold
import com.thejohnsondev.ui.generated.resources.poppins_light
import com.thejohnsondev.ui.generated.resources.poppins_medium
import com.thejohnsondev.ui.generated.resources.poppins_regular
import com.thejohnsondev.ui.generated.resources.poppins_semi_bold
import com.thejohnsondev.ui.generated.resources.poppins_thin

@Composable
fun getPoppinsFontFamily() = if (LocalInspectionMode.current) FontFamily.Default else FontFamily(
    Font(Res.font.poppins_regular),
    Font(Res.font.poppins_medium, weight = FontWeight.Medium),
    Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
    Font(Res.font.poppins_bold, FontWeight.Bold),
    Font(Res.font.poppins_black, FontWeight.Black),
    Font(Res.font.poppins_light, FontWeight.Light),
    Font(Res.font.poppins_thin, FontWeight.Thin)
)

@Composable
fun getFontFamily() = getPoppinsFontFamily() // Change this to any other font family when needed

@Composable
fun getCustomTypography() = Typography(
    // Display Large - Montserrat 57/64 . -0.25px
    displayLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
    ),

    // Display Medium - Montserrat 45/52 . 0px
    displayMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
    ),

    // Display Small - Montserrat 36/44 . 0px
    displaySmall = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),

    // Headline Large - Montserrat 32/40 . 0px
    headlineLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
    ),

    // Headline Medium - Montserrat 28/36 . 0px
    headlineMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
    ),

    // Headline Small - Montserrat 24/32 . 0px
    headlineSmall = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
    ),

    // Title Large - Montserrat 22/28 . 0px
    titleLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.5).sp,
    ),

    // Title Medium - Montserrat 16/24 . 0.15px
    titleMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 15.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp,
    ),

    // Title Small - Montserrat 14/20 . 0.1px
    titleSmall = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // Label Large - Montserrat 14/20 . 0.1px
    labelLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // Label Medium - Montserrat 12/16 . 0.5px
    labelMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),

    // Label Small - Montserrat 11/16 . 0.5px
    labelSmall = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W500,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
    ),

    // Body Large - Montserrat 16/24 . 0.5px
    bodyLarge = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),

    // Body Medium - Montserrat 14/20 . 0.25px
    bodyMedium = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    // Body Small - Montserrat 12/16 . 0.4px
    bodySmall = TextStyle(
        fontFamily = getFontFamily(),
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
    ),
)

fun getDefaultTypography() = Typography()