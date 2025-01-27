package com.shivangi.launcherLab.domain.usecase

import com.shivangi.launcherLab.domain.model.AppIconModel
import com.shivangi.launcherLab.domain.repository.AppIconRepository

class GetRemoteAppIconUseCase(
    private val repository: AppIconRepository
) {
    suspend operator fun invoke(): List<AppIconModel> {
        // This use case simply fetches from the repository
        return repository.getRemoteAppIcons()
    }
}
