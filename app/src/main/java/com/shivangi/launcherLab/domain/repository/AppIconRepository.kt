package com.shivangi.launcherLab.domain.repository

import com.shivangi.launcherLab.domain.model.AppIconModel

interface AppIconRepository {
    suspend fun getRemoteAppIcons(): List<AppIconModel>
}
