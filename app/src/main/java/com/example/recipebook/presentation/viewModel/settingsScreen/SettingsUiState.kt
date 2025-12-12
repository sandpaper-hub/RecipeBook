package com.example.recipebook.presentation.viewModel.settingsScreen

import com.example.recipebook.domain.model.ThemeMode

data class SettingsUiState(
    val uid: String = "",
    val imageUrl: String? = null,
    val fullName: String = "",
    val nickName: String = "",
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: String? = null,
    val errorMessage: String? = null
)