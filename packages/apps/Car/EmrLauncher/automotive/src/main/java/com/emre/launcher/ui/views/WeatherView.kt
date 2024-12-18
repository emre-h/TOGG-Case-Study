package com.emre.launcher.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.emre.launcher.ui.viewmodels.WeatherViewModel

@Composable
fun WeatherView(viewModel: WeatherViewModel) {
    val weather by viewModel.weather.observeAsState()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    GradientCard(
        modifier = Modifier.width(screenWidth*0.3.dp),
        colors = listOf(Color(0xff1976D2), Color(0xFF6dd5ed), Color(0xFF6dd5ed))
    )
    {
        Column(modifier = Modifier.padding(16.dp)) {
            GothamText("Weather", 24.sp, Color(0xFF121212), FontWeight.Normal)

            /*Text("City: Istanbul")
            Text("Temperature: 10.6°C")
            Text("Description: clearsky")
            weather?.let {
                Text("City: ${it.city}")
                Text("Temperature: ${it.temperature}°C")
                Text("Description: ${it.description}")
            }*/

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}