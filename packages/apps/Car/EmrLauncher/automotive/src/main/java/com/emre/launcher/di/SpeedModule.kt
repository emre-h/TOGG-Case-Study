package com.emre.launcher.di

import com.emre.launcher.domain.usecases.SpeedViewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// Hilt Module for Dependency Injection
@Module
@InstallIn(SingletonComponent::class)
object SpeedModule {
    @Provides
    fun provideSpeedUseCase(): SpeedViewUseCase {
        return SpeedViewUseCase()
    }
}