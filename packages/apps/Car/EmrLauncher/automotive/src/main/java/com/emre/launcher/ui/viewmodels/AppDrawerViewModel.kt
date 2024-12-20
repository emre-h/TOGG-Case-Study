package com.emre.launcher.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.emre.launcher.data.models.AppInfo
import com.emre.launcher.domain.usecases.GetLaunchableAppsUseCase

class AppDrawerViewModel(private val getLaunchableAppsUseCase: GetLaunchableAppsUseCase) {
    private val _apps = mutableStateOf<List<AppInfo>>(emptyList())
    val apps: State<List<AppInfo>> = _apps

    fun loadApps() {
        _apps.value = getLaunchableAppsUseCase()
    }
}