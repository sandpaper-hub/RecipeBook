package com.example.recipebook.presentation.viewModel.themeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.model.ThemeMode
import com.example.recipebook.domain.useCase.settings.changeTheme.ChangeThemeUseCase
import com.example.recipebook.domain.useCase.settings.observeTheme.ObserveThemeUseCase
import com.example.recipebook.presentation.viewModel.themeScreen.model.ThemeEvent
import com.example.recipebook.presentation.viewModel.themeScreen.model.ThemeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val observeThemeUseCase: ObserveThemeUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
) : ViewModel() {

    private val _event = MutableSharedFlow<ThemeEvent>()
    val event = _event.asSharedFlow()
    private val _uiState = MutableStateFlow(ThemeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observeAppTheme()
    }

    private fun observeAppTheme() {
        viewModelScope.launch {
            observeThemeUseCase.execute()
                .collect { themeMode ->
                    _uiState.update {
                        it.copy(themeMode = themeMode)
                    }
                }
        }
    }

    fun changeTheme(mode: ThemeMode) {
        viewModelScope.launch {
            changeThemeUseCase.execute(mode)
        }
    }

    fun onBack() {
        viewModelScope.launch {
            _event.emit(ThemeEvent.OnBack)
        }
    }
}