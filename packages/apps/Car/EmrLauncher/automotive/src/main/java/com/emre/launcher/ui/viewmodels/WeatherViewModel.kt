package com.emre.launcher.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emre.launcher.data.models.Weather
import com.emre.launcher.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.launch

class WeatherViewModel(private val getWeatherUseCase: GetWeatherUseCase) : ViewModel() {
    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                _weather.value = getWeatherUseCase.execute(city)
            } catch (e: Exception) {
                // Error handling
            }
        }
    }
}