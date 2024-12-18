package com.emre.launcher.data.repository

import android.content.pm.PackageManager
import com.emre.launcher.data.models.AppInfo

class AppRepositoryImpl(private val packageManager: PackageManager) : AppRepository {
    override fun getLaunchableApps(): List<AppInfo> {
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val launchableApps = apps.filter {
            packageManager.getLaunchIntentForPackage(it.packageName) != null
        }

        return launchableApps.map {
            AppInfo(
                name = it.loadLabel(packageManager).toString(),
                packageName = it.packageName,
                icon = it.loadIcon(packageManager)
            )
        }
    }
}