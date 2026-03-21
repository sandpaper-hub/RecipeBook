package com.example.recipebook.presentation.viewModel.themeScreen.model

sealed interface ThemeEvent {
    data object OnBack: ThemeEvent
}