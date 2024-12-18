package com.emre.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.emre.launcher.data.api.WeatherAPI
import com.emre.launcher.data.repository.WeatherRepositoryImpl
import com.emre.launcher.domain.usecase.GetWeatherUseCase
import com.emre.launcher.ui.theme.EmrLauncherTheme
import com.emre.launcher.ui.viewmodels.WeatherViewModel
import com.emre.launcher.ui.views.MapsView
import com.emre.launcher.ui.views.SpeedView
import com.emre.launcher.ui.views.TimeCard
import com.emre.launcher.ui.views.WeatherView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        val weatherApi = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)

        val weatherRepository = WeatherRepositoryImpl(weatherApi)
        val getWeatherUseCase = GetWeatherUseCase(weatherRepository)

        weatherViewModel = WeatherViewModel(getWeatherUseCase)

        setContent {
            EmrLauncherTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = com.emre.launcher.ui.theme.LauncherBackgroundColor
                ) { innerPadding ->
                    Row {
                        Column {
                            WeatherView(viewModel = weatherViewModel)
                            TimeCard()
                            SpeedView()
                        }
                        MapsView()
                    }
                }
            }
        }
        //weatherViewModel.loadWeather("Istanbul")
    }
}