package com.example.physicsformulas

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object PhysicsPalette {
    val Ink = Color(0xFF363237)
    val Aluminum = Color(0xFFD09683)
    val Paper = Color(0xFFFFF8F4)
    val Ruby = Color(0xFFD09683)
    val Surface = Color(0xFF73605B)
    val SurfaceRaised = Color(0xFF2D4262)
    val Border = Color(0xFFD09683)
    val BorderStrong = Color(0xFFD09683)
    val InkMuted = Color(0xFFF0D7CD)
}

private val PhysicsColors = lightColorScheme(
    primary = PhysicsPalette.Ruby,
    onPrimary = PhysicsPalette.Paper,
    secondary = PhysicsPalette.Aluminum,
    onSecondary = PhysicsPalette.Ink,
    background = PhysicsPalette.Ink,
    onBackground = PhysicsPalette.Paper,
    surface = PhysicsPalette.Surface,
    onSurface = PhysicsPalette.Paper,
    outline = PhysicsPalette.Aluminum,
)

val Typography.cardPrompt: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 19.sp,
        lineHeight = 30.sp,
    )

private val PhysicsTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp,
        lineHeight = 40.sp,
    ),
)

@Composable
fun PhysicsFormulasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PhysicsColors,
        typography = PhysicsTypography,
        content = content,
    )
}
