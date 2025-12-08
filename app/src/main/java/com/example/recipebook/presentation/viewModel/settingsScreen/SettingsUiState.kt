package com.example.recipebook.presentation.viewModel.settingsScreen

data class SettingsUiState(
    val uid: String = "",
    val imageUrl: String? = null,
    val fullName: String = "",
    val nickName: String = "",
    val errorMessage: String? = null
)