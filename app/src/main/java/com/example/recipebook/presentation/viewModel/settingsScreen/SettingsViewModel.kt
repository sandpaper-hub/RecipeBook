package com.example.recipebook.presentation.viewModel.settingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {
    var uiState by mutableStateOf(SettingsUiState())
        private set

    init {
        observeUserProfile()
        observeTheme()
        observeLanguage()
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            profileInteractor.observerUserProfile()
                .catch { error ->
                    uiState = uiState.copy(errorMessage = error.message)
                }
                .collect { userProfile ->
                    uiState = uiState.copy(
                        uid = userProfile.uid,
                        fullName = userProfile.fullName,
                        nickName = userProfile.nickName,
                        imageUrl = userProfile.photoUrl
                    )
                }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            settingsInteractor.getTheme()
                .collect { themeMode ->
                    uiState = uiState.copy(themeMode = themeMode)
                }
        }
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            settingsInteractor.observeSavedLanguage()
                .collect { language ->
                    uiState = uiState.copy(language = language)
                }
        }
    }
    fun logOut() {
        viewModelScope.launch {
            settingsInteractor.logOut()
        }
    }
}