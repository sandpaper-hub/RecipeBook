package com.example.recipebook.presentation.viewModel.cookingScreen.model

sealed interface CookingEvent {
    data class GoToPage(val index: Int) : CookingEvent
}