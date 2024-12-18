import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SUVTopView(
    bodyColor: Color = Color.Black,
    wheelColor: Color = Color.Gray,
    windowColor: Color = Color.LightGray,
    hasWindows: Boolean = true,
    hasHeadlights: Boolean = true,
    modifier: Modifier = Modifier.size(300.dp)
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val carBodyPath = Path().apply {
            // Aracın ana gövde hatları (daha detaylı)
            moveTo(width * 0.2f, height * 0.6f)
            cubicTo(
                width * 0.1f, height * 0.4f,
                width * 0.15f, height * 0.2f,
                width * 0.3f, height * 0.2f
            )
            lineTo(width * 0.7f, height * 0.2f)
            cubicTo(
                width * 0.85f, height * 0.2f,
                width * 0.9f, height * 0.4f,
                width * 0.8f, height * 0.6f
            )
            lineTo(width * 0.2f, height * 0.6f) // Kapat
            close()
        }

        val frontWindowPath = Path().apply {
            // Ön cam
            moveTo(width * 0.35f, height * 0.2f)
            lineTo(width * 0.65f, height * 0.2f)
            lineTo(width * 0.6f, height * 0.35f)
            lineTo(width * 0.4f, height * 0.35f)
            close()
        }

        val backWindowPath = Path().apply {
            // Arka cam
            moveTo(width * 0.3f, height * 0.45f)
            lineTo(width * 0.7f, height * 0.45f)
            lineTo(width * 0.65f, height * 0.6f)
            lineTo(width * 0.35f, height * 0.6f)
            close()
        }

        val wheel1Path = Path().apply {
            // Sol tekerlek
            addOval(androidx.compose.ui.geometry.Rect(left = width*0.25f, top = height*0.7f, right = width*0.35f, bottom = height*0.8f))

        }

        val wheel2Path = Path().apply {
            // Sağ tekerlek
            addOval(androidx.compose.ui.geometry.Rect(left = width*0.65f, top = height*0.7f, right = width*0.75f, bottom = height*0.8f))
        }

        drawPath(
            path = carBodyPath,
            color = Color.Gray,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )

        drawPath(
            path = frontWindowPath,
            color = Color.LightGray,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
        drawPath(
            path = backWindowPath,
            color = Color.LightGray,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
        drawPath(
            path = wheel1Path,
            color = Color.Black,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )
        drawPath(
            path = wheel2Path,
            color = Color.Black,
            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
        )


    }
}

