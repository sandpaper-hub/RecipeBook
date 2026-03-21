package com.example.recipebook.presentation.viewModel.themeScreen.model

import com.example.recipebook.domain.model.ThemeMode

data class ThemeUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)