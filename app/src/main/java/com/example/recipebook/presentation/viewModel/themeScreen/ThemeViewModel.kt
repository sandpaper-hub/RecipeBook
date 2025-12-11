package com.example.recipebook.presentation.viewModel.themeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.settings.SettingsInteractor
import com.example.recipebook.domain.model.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    val theme = settingsInteractor.getTheme().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ThemeMode.SYSTEM
    )

    fun changeTheme(mode: ThemeMode) {
        viewModelScope.launch {
            settingsInteractor.changeTheme(mode)
        }
    }
}