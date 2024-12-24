package com.emre.launcher.ui.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Speedometer(
    currentSpeed: Float,
    maxSpeed: Float = 240f,
    modifier: Modifier = Modifier.size(160.dp)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val startAngle = 135f // Initial arc angle
            val sweepAngle = 270f // Total arc angle

            drawArc(
                color = Color(0xffa8dadc),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = 15.dp.toPx())
            )

            val speedSweepAngle = (currentSpeed / maxSpeed) * sweepAngle
            drawArc(
                color = Color(0xff1d3557),
                startAngle = startAngle,
                sweepAngle = speedSweepAngle,
                useCenter = false,
                style = Stroke(width = 15.dp.toPx())
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GothamText("${currentSpeed.toInt()} km/h", 28.sp, Color.White, FontWeight.Normal)
            GothamText("${maxSpeed.toInt()} km/h", 18.sp, Color.White, FontWeight.Bold)
        }
    }
}
