package com.example.physicsformulas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIDevice
import platform.UIKit.UIDeviceOrientation
import platform.UIKit.UIScreen

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberAppDimensions(): AppDimensions {
    return remember {
        val widthDp = UIScreen.mainScreen.bounds.useContents { size.width.toInt() }

        val isLandscape =
            UIDevice.currentDevice.orientation == UIDeviceOrientation.UIDeviceOrientationLandscapeLeft ||
                    UIDevice.currentDevice.orientation == UIDeviceOrientation.UIDeviceOrientationLandscapeRight

        val orientation = if (isLandscape) Orientation.LANDSCAPE else Orientation.PORTRAIT

        val type = when {
            widthDp < 360 -> ScreenType.PHONE_SMALL
            widthDp < 600 -> ScreenType.PHONE
            widthDp < 840 -> ScreenType.PHONE_LARGE
            else          -> ScreenType.TABLET
        }

        dimensionsFor(type, orientation)
    }
}