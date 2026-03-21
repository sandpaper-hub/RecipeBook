package com.example.recipebook.presentation.viewModel.cookingScreen.model

sealed interface CookingEvent {
    object GoBack: CookingEvent
    data class GoToPage(val index: Int) : CookingEvent
}