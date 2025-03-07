package com.emre.launcher

import CarCard
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.emre.launcher.ui.cards.EnergyCard
import com.emre.launcher.ui.cards.MapsCard
import com.emre.launcher.ui.cards.SpeedCard
import com.emre.launcher.ui.cards.TimeCard
import com.emre.launcher.ui.cards.WeatherView
import com.emre.launcher.ui.theme.EmrLauncherTheme
import com.emre.launcher.ui.viewmodels.CarViewModel
import com.emre.launcher.ui.viewmodels.SpeedViewModel
import com.emre.launcher.ui.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Get the ViewModel instances using Hilt
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val speedViewModel: SpeedViewModel by viewModels()
    private val carViewModel: CarViewModel by viewModels()

    private lateinit var mCarPropertyManager: CarPropertyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        weatherViewModel.loadWeather("Istanbul")

        setContent {
            EmrLauncherTheme {
                MainScreen()
            }
        }
        weatherViewModel.loadWeather("Istanbul")

        carViewModel.toggleDoor("frontLeft")
        mCarPropertyManager = Car.createCar(this).getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

        mCarPropertyManager.registerCallback(object : CarPropertyEventCallback {
            override fun onChangeEvent(carPropertyValue: CarPropertyValue<*>) {
                Log.d("vspeed","onChangeEvent(" + carPropertyValue.value + ")")
                speedViewModel.setSpeed(carPropertyValue.value as Float)
            }

            override fun onErrorEvent(propId: Int, zone: Int) {
                Log.d("vspeed", "error")
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_NORMAL)
    }

    @Composable
    private fun MainScreen() {
        Box {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp
            val screenWidth = configuration.screenWidthDp

            enableEdgeToEdge()

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.wallp3),
                contentDescription = "background_image",
                contentScale = ContentScale.FillBounds
            )

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                containerColor = Color(0x44050505)
            ) { innerPadding ->
                Row {
                    Column {
                        WeatherView(
                            modifier = Modifier
                                .width(screenWidth * 0.25.dp)
                                .height(screenHeight * 0.34.dp),
                            viewModel = weatherViewModel
                        )
                        CarCard(
                            modifier = Modifier
                                .width(screenWidth * 0.25.dp)
                                .height(screenHeight * 0.8.dp),
                            carState = carViewModel.carState.value
                        )
                    }
                    Column {
                        TimeCard(modifier = Modifier
                            .width(screenWidth * 0.25.dp)
                            .height(screenHeight * 0.34.dp))

                        SpeedCard(modifier = Modifier
                            .width(screenWidth * 0.25.dp)
                            .height(screenHeight * 0.34.dp), speedViewModel.speedState.value)
                        EnergyCard(modifier = Modifier
                            .width(screenWidth * 0.25.dp)
                            .height(screenHeight * 0.34.dp))
                    }
                    Column {
                        MapsCard(modifier = Modifier
                            .width(screenWidth * 0.7.dp)
                            .height(screenHeight.dp))
                    }
                }
            }
        }
    }
}