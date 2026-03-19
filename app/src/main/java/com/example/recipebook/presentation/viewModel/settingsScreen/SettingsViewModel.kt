package com.example.recipebook.presentation.viewModel.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.useCase.authentication.logout.LogOutUseCase
import com.example.recipebook.domain.useCase.settings.observeSavedLanguage.ObserveSavedLanguageUseCase
import com.example.recipebook.domain.useCase.settings.observeTheme.ObserveThemeUseCase
import com.example.recipebook.domain.useCase.userProfile.observeUserProfile.ObserveUserProfileUseCase
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
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val observeSavedLanguageUseCase: ObserveSavedLanguageUseCase,
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val logOutUseCase: LogOutUseCase,
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
            observeUserProfileUseCase.execute()
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
            observeThemeUseCase.execute()
                .collect { themeMode ->
                    _uiState.update {
                        it.copy(themeMode = themeMode)
                    }
                }
        }
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            observeSavedLanguageUseCase.execute()
                .collect { language ->
                    _uiState.update {
                        it.copy(language = language)
                    }
                }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.execute()
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