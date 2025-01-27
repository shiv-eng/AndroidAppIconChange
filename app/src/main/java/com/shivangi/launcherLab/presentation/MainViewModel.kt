package com.shivangi.launcherLab.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shivangi.launcherLab.domain.model.AppIconModel
import com.shivangi.launcherLab.domain.usecase.GetRemoteAppIconUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MainUiState {
    object Idle : MainUiState()
    object Loading : MainUiState()
    data class Success(val icons: List<AppIconModel>) : MainUiState()
    data class Error(val message: String) : MainUiState()
}

class MainViewModel(
    private val getRemoteAppIconUseCase: GetRemoteAppIconUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun fetchIconsFromServer() {
        _uiState.value = MainUiState.Loading
        viewModelScope.launch {
            try {
                val icons = getRemoteAppIconUseCase()
                _uiState.value = MainUiState.Success(icons)
            } catch (e: Exception) {
                _uiState.value = MainUiState.Error("Failed to load icons: ${e.message}")
            }
        }
    }
}
