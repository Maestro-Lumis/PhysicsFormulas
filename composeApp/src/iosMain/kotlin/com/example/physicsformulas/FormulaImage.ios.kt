package com.example.physicsformulas

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readBytes
import org.jetbrains.skia.Image as SkiaImage
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun FormulaImage(
    imageName: String,
    contentDescription: String,
    modifier: Modifier,
) {
    val resName = imageName.removeSuffix(".png")
    val bitmap = remember(resName) { loadImageBitmap(resName) }

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Fit,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun loadImageBitmap(name: String): ImageBitmap? {
    val uiImage = UIImage.imageNamed(name) ?: return null
    val data = UIImagePNGRepresentation(uiImage) ?: return null
    val bytes = data.bytes?.readBytes(data.length.toInt()) ?: return null
    return SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()
}