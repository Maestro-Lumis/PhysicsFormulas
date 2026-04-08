package com.example.physicsformulas

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

@Composable
actual fun FormulaImage(
    imageName: String,
    contentDescription: String,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val resName = imageName.removeSuffix(".png")
    val resId = context.resources.getIdentifier(resName, "drawable", context.packageName)

    if (resId != 0) {
        Image(
            painter = painterResource(resId),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Fit,
        )
    }
}