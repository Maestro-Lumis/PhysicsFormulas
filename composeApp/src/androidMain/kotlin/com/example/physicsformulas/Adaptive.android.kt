package com.example.physicsformulas

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun rememberAppDimensions(): AppDimensions {
    val configuration = LocalConfiguration.current
    return remember(configuration.screenWidthDp, configuration.orientation) {
        val orientation = if (
            configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ) Orientation.LANDSCAPE else Orientation.PORTRAIT

        val type = when {
            configuration.screenWidthDp < 360 -> ScreenType.PHONE_SMALL
            configuration.screenWidthDp < 600 -> ScreenType.PHONE
            configuration.screenWidthDp < 840 -> ScreenType.PHONE_LARGE
            else                              -> ScreenType.TABLET
        }

        dimensionsFor(type, orientation)
    }
}