package com.example.recipebook.presentation.viewModel.languageScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.language.LanguageInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val languageInteractor: LanguageInteractor
) : ViewModel() {
    var uiState by mutableStateOf(LanguageUiState())

    init {
        viewModelScope.launch {
            languageInteractor.observeSavedLanguage()
                .collect { savedCode ->
                    uiState = uiState.copy(language = savedCode)

                }
        }
    }

    fun changeApplicationLanguage(value: String) {
        viewModelScope.launch {
            languageInteractor.changeApplicationLanguage(value)
            uiState = uiState.copy(language = value)
        }
    }
}