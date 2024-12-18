package com.emre.launcher.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BatteryView(
    batteryLevel: Float,
    modifier: Modifier = Modifier.padding(start = 8.dp).width(82.dp).height(130.dp) // Boyut
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Charge amount part
            drawRoundRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset(
                    8.dp.toPx(),
                    8.dp.toPx() + (height * 0.9f) * (1f - batteryLevel)
                ),
                size = androidx.compose.ui.geometry.Size(
                    width - 16.dp.toPx(),
                    (height * 0.9f - 16.dp.toPx()) * batteryLevel
                ),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12.dp.toPx())
            )

            // Battery frame
            drawRoundRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset(0f, 0f),
                size = androidx.compose.ui.geometry.Size(width, height * 0.9f),
                style = Stroke(width = 8.dp.toPx()),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx())
            )

            // Battery header
            drawRoundRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset(width * 0.4f, -height * 0.1f),
                size = androidx.compose.ui.geometry.Size(width * 0.2f, height * 0.1f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx())
            )
        }

        // Battery percentage
        GothamText(
            text = "${(batteryLevel * 100).toInt()}%",
            size = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xff6a994e)
        )
    }
}
