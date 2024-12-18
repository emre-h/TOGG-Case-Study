package com.emre.launcher.data.models

data class WeatherResponse(
    val main: Main,
    val weather: List<WeatherDetails>,
    val name: String
)

data class Main(val temp: Double)
data class WeatherDetails(val description: String)