package com.emre.launcher.ui.views

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.emre.launcher.R
import com.emre.launcher.data.models.Car

@Composable
fun CarView(carState: Car) {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val customFont = ResourcesCompat.getFont(LocalContext.current, R.font.gotham)
    val warningTextPaint = android.graphics.Paint().apply {
        color = Color.Black.toArgb()
        textSize = 12.sp.value// Convert sp to pixels
        typeface = customFont
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.car1),
            contentDescription = "Car Top View",
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(150.dp).height(300.dp)
        )

        // Door indicators, rendering with canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val carWidth = size.width
            val carHeight = size.height

            val indicatorRadius = carWidth * 0.05f

            if (carState.isFrontLeftDoorOpen) {
                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.34f, carHeight * 0.4f),
                    end = Offset(carWidth * 0.34f, carHeight * 0.45f + 20),
                    strokeWidth = 10f,
                    cap = Stroke.DefaultCap
                )

                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.35f, carHeight * 0.4f),
                    end = Offset(carWidth * 0.35f - 40, carHeight * 0.45f + 15),
                    strokeWidth = 5f,
                    cap = Stroke.DefaultCap
                )
            }
            if (carState.isFrontRightDoorOpen) {
                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.67f, carHeight * 0.4f),
                    end = Offset(carWidth * 0.67f, carHeight * 0.45f + 20),
                    strokeWidth = 10f,
                    cap = Stroke.DefaultCap
                )

                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.67f, carHeight * 0.4f),
                    end = Offset(carWidth * 0.67f + 40, carHeight * 0.45f + 15),
                    strokeWidth = 5f,
                    cap = Stroke.DefaultCap
                )
            }
            if (carState.isBackLeftDoorOpen) {
                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.34f, carHeight * 0.55f),
                    end = Offset(carWidth * 0.34f, carHeight * 0.55f + 40),
                    strokeWidth = 10f,
                    cap = Stroke.DefaultCap
                )

                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.35f, carHeight * 0.55f),
                    end = Offset(carWidth * 0.35f - 40, carHeight * 0.60f + 15),
                    strokeWidth = 5f,
                    cap = Stroke.DefaultCap
                )
            }
            if (carState.isBackRightDoorOpen) {
                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.67f, carHeight * 0.55f),
                    end = Offset(carWidth * 0.67f, carHeight * 0.55f + 40),
                    strokeWidth = 10f,
                    cap = Stroke.DefaultCap
                )

                drawLine(
                    color = Color.Red,
                    start = Offset(carWidth * 0.67f, carHeight * 0.55f),
                    end = Offset(carWidth * 0.67f + 40, carHeight * 0.60f + 15),
                    strokeWidth = 5f,
                    cap = Stroke.DefaultCap
                )
            }
        }
    }
}

fun DrawScope.drawFlashingIndicator(center: Offset, radius: Float, alpha: Float) {
    drawCircle(
        color = Color.Red.copy(alpha = alpha),
        center = center,
        radius = radius
    )
}