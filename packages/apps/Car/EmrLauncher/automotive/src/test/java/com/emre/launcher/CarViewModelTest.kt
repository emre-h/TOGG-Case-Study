package com.emre.launcher

import com.emre.launcher.data.models.Car
import com.emre.launcher.domain.usecases.ToggleDoorUseCase
import com.emre.launcher.ui.viewmodels.CarViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CarViewModelTest {
    class TestToggleDoorUseCase : ToggleDoorUseCase() {
        override fun execute(car: Car, door: String): Car {
            return when (door) {
                "frontLeft" -> car.copy(isFrontLeftDoorOpen = !car.isFrontLeftDoorOpen)
                "frontRight" -> car.copy(isFrontRightDoorOpen = !car.isFrontRightDoorOpen)
                "backLeft" -> car.copy(isBackLeftDoorOpen = !car.isBackLeftDoorOpen)
                "backRight" -> car.copy(isBackRightDoorOpen = !car.isBackRightDoorOpen)
                else -> car
            }
        }
    }

    private lateinit var carViewModel: CarViewModel

    @Before
    fun setup() {
        val toggleDoorUseCase = TestToggleDoorUseCase()
        carViewModel = CarViewModel(toggleDoorUseCase)
    }

    @Test
    fun `toggle front left door updates state correctly`() {
        // Başlangıç durumu
        val initialCar = carViewModel.carState.value
        assertEquals(Car(), initialCar)

        // Front left kapısını aç
        carViewModel.toggleDoor("frontLeft")
        val expectedCarAfterFirstToggle = Car(isFrontLeftDoorOpen = true)
        assertEquals(expectedCarAfterFirstToggle, carViewModel.carState.value)

        // Front left kapısını tekrar kapat
        carViewModel.toggleDoor("frontLeft")
        val expectedCarAfterSecondToggle = Car(isFrontLeftDoorOpen = false)
        assertEquals(expectedCarAfterSecondToggle, carViewModel.carState.value)
    }

    @Test
    fun `toggle multiple doors updates state correctly`() {
        assertEquals(Car(), carViewModel.carState.value)

        // Open frontLeft and backRight
        carViewModel.toggleDoor("frontLeft")
        carViewModel.toggleDoor("backRight")
        val expectedCar = Car(
            isFrontLeftDoorOpen = true,
            isBackRightDoorOpen = true
        )
        assertEquals(expectedCar, carViewModel.carState.value)

        // Close frontLeft
        carViewModel.toggleDoor("frontLeft")
        val expectedCarAfterToggle = Car(
            isFrontLeftDoorOpen = false,
            isBackRightDoorOpen = true
        )
        assertEquals(expectedCarAfterToggle, carViewModel.carState.value)
    }

    @Test
    fun `invalid door name does not change state`() {
        // Başlangıç durumu
        val initialCar = carViewModel.carState.value

        // Geçersiz bir kapı adı ver
        carViewModel.toggleDoor("invalidDoor")

        // Durumun değişmediğini doğrula
        assertEquals(initialCar, carViewModel.carState.value)
    }
}
