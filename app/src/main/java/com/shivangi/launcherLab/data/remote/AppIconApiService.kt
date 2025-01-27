package com.shivangi.launcherLab.data.remote

import com.shivangi.launcherLab.data.remote.dto.AppIconDto
import retrofit2.http.GET

interface AppIconApiService {

    @GET("DynamicAppIcon/appicon.html")
    suspend fun getIcons(): List<AppIconDto>
}
