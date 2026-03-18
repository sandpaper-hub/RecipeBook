package com.example.recipebook.presentation.viewModel.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import com.example.recipebook.domain.interactor.profile.ProfileInteractor
import com.example.recipebook.presentation.viewModel.settingsScreen.model.SettingsEvent
import com.example.recipebook.presentation.viewModel.settingsScreen.model.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val profileInteractor: ProfileInteractor,
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {
    private val _event = MutableSharedFlow<SettingsEvent>()
    val event = _event.asSharedFlow()
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeUserProfile()
        observeTheme()
        observeLanguage()
    }

    private fun observeUserProfile() {
        viewModelScope.launch {
            profileInteractor.observerUserProfile()
                .catch { error ->
                    _uiState.update {
                        it.copy(errorMessage = error.message)
                    }
                }
                .collect { userProfile ->
                    _uiState.update {
                        it.copy(
                            uid = userProfile.uid,
                            fullName = userProfile.fullName,
                            nickName = userProfile.nickName,
                            imageUrl = userProfile.photoUrl
                        )
                    }
                }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            settingsInteractor.getTheme()
                .collect { themeMode ->
                    _uiState.update {
                        it.copy(themeMode = themeMode)
                    }
                }
        }
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            settingsInteractor.observeSavedLanguage()
                .collect { language ->
                    _uiState.update {
                        it.copy(language = language)
                    }
                }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            settingsInteractor.logOut()
            _event.emit(SettingsEvent.OnLogout)
        }
    }

    fun onAccountScreen() {
        viewModelScope.launch {
            _event.emit(SettingsEvent.OnAccount)
        }
    }

    fun onLanguageScreen() {
        viewModelScope.launch {
            _event.emit(SettingsEvent.OnLanguage)
        }
    }

    fun onThemeScreen() {
        viewModelScope.launch {
            _event.emit(SettingsEvent.OnTheme)
        }
    }
}