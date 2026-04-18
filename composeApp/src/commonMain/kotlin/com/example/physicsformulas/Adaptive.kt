package com.example.physicsformulas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class ScreenType {
    PHONE_SMALL,
    PHONE,
    PHONE_LARGE,
    TABLET,
}

enum class Orientation { PORTRAIT, LANDSCAPE }

data class AppDimensions(
    val screenHorizontalPadding: Dp,
    val screenVerticalPadding: Dp,
    val cardInnerPadding: Dp,
    val buttonHeight: Dp,
    val backButtonFraction: Float,
    val buttonCorner: Dp,
    val buttonSpacing: Dp,
    val sectionButtonHeight: Dp,
    val sectionSpacing: Dp,
    val cardCorner: Dp,
    val cardWeight: Float,
    val cardTopSpacing: Dp,
    val cardBottomSpacing: Dp,
    val contentMaxWidth: Dp,
    val buttonFontSize: TextUnit,
    val sectionFontSize: TextUnit,
    val kimFontSize: TextUnit,
    val labelFontSize: TextUnit,
    val hintFontSize: TextUnit,
    val formulaFontSize: TextUnit,
    val explanationFontSize: TextUnit,
    val useTwoColumnLayout: Boolean,
    val cardLabelSpacing: Dp,
)

internal fun dimensionsFor(
    type: ScreenType,
    orientation: Orientation,
): AppDimensions = when (type) {

    ScreenType.PHONE_SMALL -> AppDimensions(
        screenHorizontalPadding = 14.dp,
        screenVerticalPadding = 16.dp,
        cardInnerPadding = 20.dp,
        buttonHeight = 60.dp,
        backButtonFraction = 0.80f,
        buttonCorner = 12.dp,
        buttonSpacing = 12.dp,
        sectionButtonHeight = 60.dp,
        sectionSpacing = 12.dp,
        cardCorner = 12.dp,
        cardWeight = 0.60f,
        cardTopSpacing = 16.dp,
        cardBottomSpacing = 12.dp,
        contentMaxWidth = 420.dp,
        buttonFontSize = 18.sp,
        sectionFontSize = 18.sp,
        kimFontSize = 22.sp,
        labelFontSize = 22.sp,
        hintFontSize = 22.sp,
        formulaFontSize = 32.sp,
        explanationFontSize = 17.sp,
        useTwoColumnLayout = false,
        cardLabelSpacing = 24.dp,
    )

    ScreenType.PHONE -> AppDimensions(
        screenHorizontalPadding = 16.dp,
        screenVerticalPadding = if (orientation == Orientation.LANDSCAPE) 10.dp else 20.dp,
        cardInnerPadding = 24.dp,
        buttonHeight = if (orientation == Orientation.LANDSCAPE) 56.dp else 68.dp,
        backButtonFraction = 0.75f,
        buttonCorner = 14.dp,
        buttonSpacing = 14.dp,
        sectionButtonHeight = if (orientation == Orientation.LANDSCAPE) 56.dp else 68.dp,
        sectionSpacing = if (orientation == Orientation.LANDSCAPE) 10.dp else 12.dp,
        cardCorner = 14.dp,
        cardWeight = if (orientation == Orientation.LANDSCAPE) 0.55f else 0.60f,
        cardTopSpacing = if (orientation == Orientation.LANDSCAPE) 8.dp else 24.dp,
        cardBottomSpacing = if (orientation == Orientation.LANDSCAPE) 8.dp else 16.dp,
        contentMaxWidth = 600.dp,
        buttonFontSize = 20.sp,
        sectionFontSize = 20.sp,
        kimFontSize = 24.sp,
        labelFontSize = 24.sp,
        hintFontSize = 24.sp,
        formulaFontSize = 38.sp,
        explanationFontSize = 18.sp,
        useTwoColumnLayout = false,
        cardLabelSpacing = 28.dp,
    )

    ScreenType.PHONE_LARGE -> AppDimensions(
        screenHorizontalPadding = 20.dp,
        screenVerticalPadding = if (orientation == Orientation.LANDSCAPE) 12.dp else 24.dp,
        cardInnerPadding = 28.dp,
        buttonHeight = if (orientation == Orientation.LANDSCAPE) 56.dp else 70.dp,
        backButtonFraction = 0.65f,
        buttonCorner = 14.dp,
        buttonSpacing = 14.dp,
        sectionButtonHeight = if (orientation == Orientation.LANDSCAPE) 56.dp else 70.dp,
        sectionSpacing = if (orientation == Orientation.LANDSCAPE) 12.dp else 14.dp,
        cardCorner = 14.dp,
        cardWeight = if (orientation == Orientation.LANDSCAPE) 0.52f else 0.62f,
        cardTopSpacing = if (orientation == Orientation.LANDSCAPE) 8.dp else 28.dp,
        cardBottomSpacing = if (orientation == Orientation.LANDSCAPE) 8.dp else 18.dp,
        contentMaxWidth = 560.dp,
        buttonFontSize = 20.sp,
        sectionFontSize = 20.sp,
        kimFontSize = 30.sp,
        labelFontSize = 30.sp,
        hintFontSize = 30.sp,
        formulaFontSize = 38.sp,
        explanationFontSize = 18.sp,
        useTwoColumnLayout = orientation == Orientation.LANDSCAPE,
        cardLabelSpacing = 32.dp,
    )

    ScreenType.TABLET -> AppDimensions(
        screenHorizontalPadding = 32.dp,
        screenVerticalPadding = 32.dp,
        cardInnerPadding = 36.dp,
        buttonHeight = 72.dp,
        backButtonFraction = 0.45f,
        buttonCorner = 16.dp,
        buttonSpacing = 16.dp,
        sectionButtonHeight = 72.dp,
        sectionSpacing = 18.dp,
        cardCorner = 16.dp,
        cardWeight = 0.58f,
        cardTopSpacing = 32.dp,
        cardBottomSpacing = 24.dp,
        contentMaxWidth = 640.dp,
        buttonFontSize = 22.sp,
        sectionFontSize = 22.sp,
        kimFontSize = 34.sp,
        labelFontSize = 36.sp,
        hintFontSize = 36.sp,
        formulaFontSize = 44.sp,
        explanationFontSize = 20.sp,
        useTwoColumnLayout = true,
        cardLabelSpacing = 40.dp,
    )
}

val LocalAppDimensions = compositionLocalOf {
    dimensionsFor(ScreenType.PHONE, Orientation.PORTRAIT)
}

@Composable
expect fun rememberAppDimensions(): AppDimensions