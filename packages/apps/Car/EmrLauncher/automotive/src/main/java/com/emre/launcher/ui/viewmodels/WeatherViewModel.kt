package com.emre.launcher.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emre.launcher.data.models.Weather
import com.emre.launcher.domain.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                _weather.value = getWeatherUseCase.execute(city)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}