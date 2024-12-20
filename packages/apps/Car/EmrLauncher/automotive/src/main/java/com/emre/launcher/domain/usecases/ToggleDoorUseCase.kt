package com.emre.launcher.domain.usecases

import com.emre.launcher.data.models.Car

// Use Case to toggle a door's state
class ToggleDoorUseCase {

    fun execute(car: Car, door: String): Car {
        return when (door) {
            "frontLeft" -> car.copy(isFrontLeftDoorOpen = !car.isFrontLeftDoorOpen)
            "frontRight" -> car.copy(isFrontRightDoorOpen = !car.isFrontRightDoorOpen)
            "backLeft" -> car.copy(isBackLeftDoorOpen = !car.isBackLeftDoorOpen)
            "backRight" -> car.copy(isBackRightDoorOpen = !car.isBackRightDoorOpen)
            else -> car
        }
    }
}