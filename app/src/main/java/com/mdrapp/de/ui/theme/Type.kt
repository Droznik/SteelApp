package com.mdrapp.de.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mdrapp.de.R


val interFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_bold, FontWeight.Bold),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_medium, FontWeight.Medium),
)

val typography = MdrTypography(
    regular = TextStyle(fontFamily = interFontFamily, fontWeight = FontWeight.Normal),
    medium = TextStyle(fontFamily = interFontFamily, fontWeight = FontWeight.Medium),
    bold = TextStyle(fontFamily = interFontFamily, fontWeight = FontWeight.Bold),
    semiBold = TextStyle(fontFamily = interFontFamily, fontWeight = FontWeight.SemiBold),

    title = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        lineHeight = 38.sp,
        letterSpacing = (-1.5).sp,
        fontSize = 34.sp
    ),
    description = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    primaryTextField = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

@Immutable
data class MdrTypography(
    val regular: TextStyle,
    val medium: TextStyle,
    val bold: TextStyle,
    val semiBold: TextStyle,

    val title: TextStyle,
    val description: TextStyle,
    val primaryTextField: TextStyle
)

val LocalMdrTypography = staticCompositionLocalOf<MdrTypography> {
    error("No typography provided")
}