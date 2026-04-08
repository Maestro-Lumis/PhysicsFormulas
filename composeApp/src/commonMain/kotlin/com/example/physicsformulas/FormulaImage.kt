package com.example.physicsformulas

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun FormulaImage(
    imageName: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
)