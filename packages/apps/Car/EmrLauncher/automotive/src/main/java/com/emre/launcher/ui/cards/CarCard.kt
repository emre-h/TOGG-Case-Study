import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emre.launcher.R
import com.emre.launcher.data.models.Car
import com.emre.launcher.ui.cards.GradientCard

@Composable
fun CarCard(modifier: Modifier, carState: Car) {
    GradientCard (
        modifier = modifier,
        colors = listOf(Color(0xff074799), Color(0xff009990))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            // Car View with flashing indicators
            CarView(carState = carState)

            // Door control buttons at the bottom
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f)) // Push buttons to the bottom
            }
        }
    }
}

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

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Car PNG
        Image(
            painter = painterResource(id = R.drawable.car1),
            contentDescription = "Car Top View",
            contentScale = ContentScale.Fit,
            modifier = Modifier.width(200.dp).height(350.dp)
        )

        // Door indicators
        Canvas(modifier = Modifier.fillMaxSize()) {
            val carWidth = size.width
            val carHeight = size.height

            val indicatorRadius = carWidth * 0.05f

            if (carState.isFrontLeftDoorOpen) {
                drawFlashingIndicator(
                    Offset(carWidth * 0.55f, carHeight * 0.2f),
                    indicatorRadius,
                    alpha
                )
            }
            if (carState.isFrontRightDoorOpen) {
                drawFlashingIndicator(
                    Offset(carWidth * 0.55f, carHeight * 0.8f),
                    indicatorRadius,
                    alpha
                )
            }
            if (carState.isBackLeftDoorOpen) {
                drawFlashingIndicator(
                    Offset(carWidth * 0.3f, carHeight * 0.2f),
                    indicatorRadius,
                    alpha
                )
            }
            if (carState.isBackRightDoorOpen) {
                drawFlashingIndicator(
                    Offset(carWidth * 0.3f, carHeight * 0.8f),
                    indicatorRadius,
                    alpha
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