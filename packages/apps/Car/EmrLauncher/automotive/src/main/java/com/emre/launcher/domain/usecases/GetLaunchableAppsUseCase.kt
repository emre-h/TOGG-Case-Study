package com.emre.launcher.domain.usecases

import com.emre.launcher.data.models.AppInfo
import com.emre.launcher.data.repository.AppRepository

class GetLaunchableAppsUseCase(private val repository: AppRepository) {
    operator fun invoke(): List<AppInfo> {
        return repository.getLaunchableApps()
    }
}