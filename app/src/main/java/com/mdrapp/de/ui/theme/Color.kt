package com.mdrapp.de.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
object Colors {
    val Black = Color(0xFF212121)
    val White = Color(0xFFFFFFFF)
    val GrayBG = Color(0xFFF5F5F5)
    val Gray300 = Color(0xFFD9D9D9)
    val Gray400 = Color(0xFFB8B8B8)
    val Gray600 = Color(0xFF6D7578)
    val Gray900 = Color(0xFF414141)
    val Green = Color(0xFF80C22F)
    val Blue = Color(0xFF1D7D8B)
    val Red600 = Color(0xFFDC2626)
}

val lightColorScheme = MdrColors(
    primaryBackground = Colors.White,
    snackBarBackground = Colors.Black,
    snackBarText = Colors.White,
    titleText = Colors.Black,
    primaryText = Colors.Gray900,
    secondaryText = Colors.Gray600,
    divider = Colors.Gray400,
    appGreen = Colors.Green,
    paginator = Colors.Gray300,
    appBlue = Colors.Blue,
    bg = Colors.GrayBG,
    error = Colors.Red600,
    lightText = Colors.White,
    linkText = Colors.Blue,
    primaryLoader = Colors.Green
)

@Immutable
data class MdrColors(
    val primaryBackground: Color,
    val snackBarBackground: Color,
    val snackBarText: Color,
    val titleText: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val divider: Color,
    val appGreen: Color,
    val appBlue: Color,
    val paginator: Color,
    val bg: Color,
    val error: Color,
    val lightText: Color,
    val linkText: Color,
    val primaryLoader: Color,
)

val LocalMdrColors = staticCompositionLocalOf<MdrColors> {
    error("No colors provided")
}