package com.example.recipebook.presentation.viewModel.languageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.language.SettingsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {
    var uiState by mutableStateOf(LanguageUiState())

    init {
        viewModelScope.launch {
            settingsInteractor.observeSavedLanguage()
                .collect { savedCode ->
                    uiState = uiState.copy(language = savedCode)

                }
        }
    }

    fun changeApplicationLanguage(value: String) {
        viewModelScope.launch {
            settingsInteractor.changeApplicationLanguage(value)
            uiState = uiState.copy(language = value)
        }
    }
}