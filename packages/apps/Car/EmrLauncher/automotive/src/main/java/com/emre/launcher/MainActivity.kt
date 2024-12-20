package com.emre.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.emre.launcher.data.api.WeatherAPI
import com.emre.launcher.data.repository.WeatherRepositoryImpl
import com.emre.launcher.domain.usecase.GetWeatherUseCase
import com.emre.launcher.ui.theme.EmrLauncherTheme
import com.emre.launcher.ui.viewmodels.WeatherViewModel
import com.emre.launcher.ui.views.ChargeView
import com.emre.launcher.ui.views.MapsView
import com.emre.launcher.ui.views.SpeedView
import com.emre.launcher.ui.views.TimeCard
import com.emre.launcher.ui.views.WeatherView
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
                Box {
                    val systemUiController = rememberSystemUiController()

                    // Status bar stilini ve rengini ayarla
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent, // Şeffaflık
                        darkIcons = true // İkonlar koyu renk olsun
                    )

                    // Navigation bar rengini ayarla
                    systemUiController.setNavigationBarColor(
                        color = Color.Black, // Siyah
                        darkIcons = false // İkonlar açık renk olsun
                    )
                    enableEdgeToEdge()
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.wallp3),
                        contentDescription = "background_image",
                        contentScale = ContentScale.FillBounds
                    )
                    Scaffold(
                        modifier = Modifier.fillMaxSize().padding(top = 80.dp),
                        containerColor = Color(0x55050505)
                    ) { innerPadding ->
                        Row {
                            Column {
                                Row {
                                    WeatherView(viewModel = weatherViewModel)
                                    TimeCard()
                                }

                                Row {
                                    SpeedView()
                                    ChargeView()
                                }
                            }
                            Column {
                                MapsView()
                                TimeCard()
                            }
                        }
                    }
                }
            }
        }
        //weatherViewModel.loadWeather("Istanbul")
    }
}