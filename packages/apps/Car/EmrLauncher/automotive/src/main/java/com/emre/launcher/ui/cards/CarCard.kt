import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.emre.launcher.data.models.Car
import com.emre.launcher.ui.cards.GradientCard
import com.emre.launcher.ui.views.CarView

@Composable
fun CarCard(modifier: Modifier, carState: Car) {
    GradientCard (
        modifier = modifier,
        colors = listOf(Color(0xff074799), Color(0xff009990))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CarView(carState = carState)
        }
    }
}