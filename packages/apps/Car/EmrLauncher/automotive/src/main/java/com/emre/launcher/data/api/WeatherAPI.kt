package com.emre.launcher.data.api

import com.emre.launcher.data.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        //@Query("appid") apiKey: String = "3f642054edadbb209877c10979f79798",
        @Query("appid") apiKey: String = "demo",
        @Query("units") units: String = "metric"
    ): WeatherResponse
}