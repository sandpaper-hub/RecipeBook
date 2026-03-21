package com.example.recipebook.presentation.viewModel.settingsScreen.model

sealed interface SettingsEvent {
    data object OnAccount: SettingsEvent
    data object OnLanguage: SettingsEvent
    data object OnTheme: SettingsEvent
    data object OnLogout: SettingsEvent
}