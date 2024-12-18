package com.emre.launcher.data.repository

import com.emre.launcher.data.models.Weather

interface WeatherRepository {
    suspend fun getWeather(city: String): Weather
}