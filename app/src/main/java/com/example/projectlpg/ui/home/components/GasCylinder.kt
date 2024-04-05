package com.example.projectlpg.ui.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun GasCylinderProgressBar(progress: Float, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val cylinderWidth = size.width
        val cylinderHeight = size.height
        val fillHeight = progress / 100 * cylinderHeight

        // Draw cylinder outline
        drawRoundRect(
            color = Color.Black,
            size = Size(cylinderWidth, cylinderHeight),
            cornerRadius = CornerRadius(x = 50f, y = 50f)
        )

        // Draw gas fill
        if (fillHeight > 0) {
            drawRoundRect(
                color = Color.Green,
                topLeft = Offset(x = 0f, y = cylinderHeight - fillHeight),
                size = Size(cylinderWidth, fillHeight),
                cornerRadius = CornerRadius(x = 50f, y = 50f)
            )
        }
    }
}
