package com.example.recipebook.presentation.viewModel.languageScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import com.example.recipebook.presentation.viewModel.languageScreen.model.LanguageEvent
import com.example.recipebook.presentation.viewModel.languageScreen.model.LanguageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val _uiEvents = MutableSharedFlow<LanguageEvent>()
    val events = _uiEvents.asSharedFlow()
    private val _uiState = MutableStateFlow(LanguageUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsInteractor.observeSavedLanguage()
                .collect { savedCode ->
                    _uiState.update {
                        it.copy(language = savedCode)
                    }
                }
        }
    }

    fun changeApplicationLanguage(value: String) {
        viewModelScope.launch {
            settingsInteractor.changeApplicationLanguage(value)
            _uiState.update {
                it.copy(language = value)
            }
        }
    }

    fun goBack() {
        viewModelScope.launch {
            _uiEvents.emit(LanguageEvent.GoBack)
        }
    }
}