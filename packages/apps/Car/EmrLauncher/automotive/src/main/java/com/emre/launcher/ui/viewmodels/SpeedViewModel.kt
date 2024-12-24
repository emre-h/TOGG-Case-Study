package com.emre.launcher.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.ViewModel
import com.emre.launcher.domain.usecases.SpeedViewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpeedViewModel @Inject constructor(
    private val speedViewUseCase: SpeedViewUseCase
) : ViewModel() {

    private val _speedState = mutableFloatStateOf(0f)
    val speedState: State<Float> get() = _speedState

    fun setSpeed(speed: Float) {
        _speedState.floatValue = speedViewUseCase.execute(speed)
    }
}