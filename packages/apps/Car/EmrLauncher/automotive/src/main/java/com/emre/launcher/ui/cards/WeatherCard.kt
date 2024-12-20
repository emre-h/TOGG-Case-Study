package com.emre.launcher.ui.cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emre.launcher.R
import com.emre.launcher.ui.viewmodels.WeatherViewModel
import com.emre.launcher.ui.views.GothamText

@Composable
fun WeatherView(modifier: Modifier, viewModel: WeatherViewModel) {
    val weather by viewModel.weather.observeAsState()
    val weatherIconSize = 78.dp

    GradientCard(
        modifier = modifier,
        colors = listOf(Color(0xff1976D2), Color(0xFF6dd5ed), Color(0xFF6dd5ed))
    )
    {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.TopStart)
        ) {
            GothamText("Weather", 20.sp, Color(0xFF121212), FontWeight.Normal)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_wb_sunny_24),
                contentDescription = "Weather",
                modifier = Modifier
                    .height(weatherIconSize)
                    .width(weatherIconSize)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                /*weather?.let {
                    Text("City: ${it.city}")
                    Text("Temperature: ${it.temperature}°C")
                    Text("Description: ${it.description}")
                }*/
                GothamText("Istanbul", 32.sp, Color(0xFF121212), FontWeight.Bold)
                Spacer(modifier = Modifier.height(10.dp))
                GothamText("10.6°C", 40.sp, Color(0xFF121212), FontWeight.Normal)
                Spacer(modifier = Modifier.height(10.dp))
                GothamText("Clear", 24.sp, Color(0xFF121212), FontWeight.Normal)
            }
        }
    }
}