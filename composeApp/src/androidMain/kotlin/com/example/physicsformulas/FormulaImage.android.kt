package com.example.physicsformulas

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun FormulaImage(
    imageName: String,
    contentDescription: String,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val bitmap by produceState<ImageBitmap?>(null, imageName) {
        val resName = imageName.removeSuffix(".png")
        val resId = context.resources.getIdentifier(
            resName, "drawable", context.packageName
        )
        value = if (resId != 0) {
            BitmapFactory.decodeResource(context.resources, resId)?.asImageBitmap()
        } else {
            android.util.Log.e("FormulaImage", "Resource NOT found: $resName")
            null
        }
    }

    bitmap?.let {
        Image(
            bitmap = it,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Fit,
        )
    }
}