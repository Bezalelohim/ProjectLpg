package com.example.projectlpg.ui.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GasCylinder(progress: Float, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val width = size.width
            val height = size.height
            val cylinderTopHeight = height * 0.2f // Adjust the height of the cylinder's top cap
            val cylinderColor = Color.Black
            val cylinderCapColor = Color.DarkGray
            val gasColor = Color.Red

            // Draw cylinder top cap
            val topCapPath = Path().apply {
                moveTo(0f, cylinderTopHeight / 2)
                quadraticTo(
                    width / 2, 0f,
                    width, cylinderTopHeight / 2
                )
                lineTo(width, cylinderTopHeight)
                quadraticTo(
                    width / 2, cylinderTopHeight * 1.5f,
                    0f, cylinderTopHeight
                )
                close()
            }
            drawPath(path = topCapPath, color = cylinderCapColor)

            // Draw cylinder body
            drawRoundRect(
                color = cylinderColor,
                size = size.copy(height = height - cylinderTopHeight),
                topLeft = Offset(0f, cylinderTopHeight),
                cornerRadius = CornerRadius(25f, 25f)
            )

            // Draw gas level inside the cylinder
            drawRoundRect(
                color = gasColor,
                topLeft = Offset(0f, height - (height - cylinderTopHeight) * progress),
                size = size.copy(height = (height - cylinderTopHeight) * progress),
                cornerRadius = CornerRadius(25f, 25f)
            )
            val percentages = listOf("0%", "25%", "50%", "75%", "100%")
            val textPaint = Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                textSize = 40f // Set the desired text size
                color = android.graphics.Color.BLACK
            }

            percentages.forEachIndexed { index, percentage ->
                val yPos = cylinderTopHeight + (height - cylinderTopHeight) * (1 - index * 0.25f)
                drawContext.canvas.nativeCanvas.drawText(
                    percentage,
                    width + 10f, // Some padding from the cylinder
                    yPos,
                    textPaint
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GasCylinderPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GasCylinder(
                progress = 0.70f, // 75% filled for preview
                modifier = Modifier
                    .width(200.dp)
                    .height(500.dp)
            )
        }
    }
}
