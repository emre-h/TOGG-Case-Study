package com.emre.launcher

import com.emre.launcher.domain.usecases.SpeedViewUseCase
import com.emre.launcher.ui.viewmodels.SpeedViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SpeedViewModelTest {
    open class TestSpeedViewUseCase : SpeedViewUseCase() {
        override fun execute(speed: Float): Float {
            return speed
        }
    }

    private lateinit var speedViewModel: SpeedViewModel

    @Before
    fun setup() {
        val speedViewTestUseCase = TestSpeedViewUseCase()
        speedViewModel = SpeedViewModel(speedViewTestUseCase)
    }

    @Test
    fun testSpeedValue() {
        assertEquals(0f, speedViewModel.speedState.value)

        for (i in 0..10000) {
            speedViewModel.setSpeed(i.toFloat())
        }

        assertEquals(10000f, speedViewModel.speedState.value)
    }
}