package com.emre.launcher.data.repository

import com.emre.launcher.data.api.WeatherAPI
import com.emre.launcher.data.models.Weather

class WeatherRepositoryImpl(private val weatherApi: WeatherAPI) : WeatherRepository {
    override suspend fun getWeather(city: String): Weather {
        val response = weatherApi.getWeather(city)
        return Weather(
            temperature = response.main.temp,
            description = response.weather.firstOrNull()?.description ?: "",
            city = response.name
        )
    }
}