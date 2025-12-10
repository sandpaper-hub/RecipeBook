package com.example.recipebook.presentation.viewModel.languageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {
    var uiState by mutableStateOf(LanguageUiState())

    fun getSystemLanguage() {
        uiState = uiState.copy(
            language = settingsInteractor.getSystemLanguage()
        )
    }

    fun changeApplicationLanguage(value: String) {
        uiState = uiState.copy(language = value)
        settingsInteractor.changeApplicationLanguage(value)
    }
}