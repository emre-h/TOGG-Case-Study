package com.emre.launcher.data.models

// Entity representing the car's door states
data class Car(
    val isFrontLeftDoorOpen: Boolean = false,
    val isFrontRightDoorOpen: Boolean = false,
    val isBackLeftDoorOpen: Boolean = false,
    val isBackRightDoorOpen: Boolean = false
)