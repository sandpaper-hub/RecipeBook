package com.example.recipebook.presentation.viewModel.languageScreen.model

sealed interface LanguageEvent {
    object GoBack: LanguageEvent
}