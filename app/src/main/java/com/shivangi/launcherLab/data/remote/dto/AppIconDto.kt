package com.shivangi.launcherLab.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AppIconDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("imageUrl") val imageUrl: String
)
