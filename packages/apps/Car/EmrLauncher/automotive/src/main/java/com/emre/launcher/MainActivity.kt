package com.emre.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.emre.launcher.data.api.WeatherAPI
import com.emre.launcher.data.repository.WeatherRepositoryImpl
import com.emre.launcher.domain.usecase.GetWeatherUseCase
import com.emre.launcher.ui.theme.EmrLauncherTheme
import com.emre.launcher.ui.viewmodels.WeatherViewModel
import com.emre.launcher.ui.views.WeatherView
import com.google.maps.android.compose.GoogleMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        // Retrofit ve Repository başlatma
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
                    containerColor = Color(0xFF121212)
                ) { innerPadding ->
                    Row {
                        WeatherView(viewModel = weatherViewModel)
                    }
                }
            }
        }
        
        //weatherViewModel.loadWeather("Istanbul")
    }
}