package com.example.recipebook.presentation.viewModel.cookingScreen

sealed interface CookingEvent {
    data class GoToPage(val index: Int) : CookingEvent
}