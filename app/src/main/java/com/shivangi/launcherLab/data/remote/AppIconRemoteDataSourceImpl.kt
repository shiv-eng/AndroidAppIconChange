package com.shivangi.launcherLab.data.remote

import com.shivangi.launcherLab.data.remote.dto.AppIconDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppIconRemoteDataSourceImpl(
    private val api: AppIconApiService
) {
    suspend fun fetchIcons(): List<AppIconDto> = withContext(Dispatchers.IO) {
        api.getIcons()
    }
}
