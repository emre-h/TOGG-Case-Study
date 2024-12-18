package com.emre.launcher.domain.usecase

import com.emre.launcher.data.models.Weather
import com.emre.launcher.data.repository.WeatherRepository

// Domain layer -> usecases
class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {
    suspend fun execute(city: String): Weather {
        return weatherRepository.getWeather(city)
    }
}