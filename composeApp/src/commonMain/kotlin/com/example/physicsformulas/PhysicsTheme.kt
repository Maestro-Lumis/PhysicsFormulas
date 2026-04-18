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
    val Backdrop = Color(0xFF2C2A27)
    val Background = Color(0xFFF0F4F8)
    val Surface = Color(0xFFE8EEF4)
    val Card = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF6A90A8)
    val Accent = Color(0xFF4A7AA0)
    val TextPrimary = Color(0xFF1A3050)

}

private val PhysicsColors = lightColorScheme(
    primary = PhysicsPalette.Accent,
    onPrimary = PhysicsPalette.Card,
    secondary = PhysicsPalette.TextSecondary,
    onSecondary = PhysicsPalette.Card,
    background = PhysicsPalette.Background,
    onBackground = PhysicsPalette.TextPrimary,
    surface = PhysicsPalette.Surface,
    onSurface = PhysicsPalette.TextPrimary,
    outline = PhysicsPalette.Accent,
)

val Typography.buttonText: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    )

val Typography.cardText: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 30.sp,
    )

val Typography.formulaText: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
    )

private val PhysicsTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 22.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 30.sp,
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
