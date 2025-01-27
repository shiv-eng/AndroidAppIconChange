package com.shivangi.launcherLab.data.repository

import com.shivangi.launcherLab.data.remote.AppIconRemoteDataSourceImpl
import com.shivangi.launcherLab.domain.model.AppIconModel
import com.shivangi.launcherLab.domain.repository.AppIconRepository

class AppIconRepositoryImpl(
    private val remoteDataSource: AppIconRemoteDataSourceImpl
) : AppIconRepository {

    override suspend fun getRemoteAppIcons(): List<AppIconModel> {
        return remoteDataSource.fetchIcons().map { dto ->
            AppIconModel(
                id = dto.id,
                title = dto.title,
                imageUrl = dto.imageUrl
            )
        }
    }
}
