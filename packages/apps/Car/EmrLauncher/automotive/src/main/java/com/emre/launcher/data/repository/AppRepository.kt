package com.emre.launcher.data.repository

import com.emre.launcher.data.models.AppInfo

interface AppRepository {
    fun getLaunchableApps(): List<AppInfo>
}