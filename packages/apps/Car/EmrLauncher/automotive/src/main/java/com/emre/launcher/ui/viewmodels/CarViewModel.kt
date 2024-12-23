package com.emre.launcher.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.emre.launcher.data.models.Car
import com.emre.launcher.domain.usecases.ToggleDoorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    private val toggleDoorUseCase: ToggleDoorUseCase
) : ViewModel() {

    private val _carState = mutableStateOf(Car())
    val carState: State<Car> get() = _carState

    // Toggle the door state based on the door name
    fun toggleDoor(door: String) {
        _carState.value = toggleDoorUseCase.execute(_carState.value, door)
    }
}