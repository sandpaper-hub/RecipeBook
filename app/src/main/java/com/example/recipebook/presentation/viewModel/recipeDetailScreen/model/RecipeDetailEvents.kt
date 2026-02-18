package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

sealed interface RecipeDetailEvent {
    object GoBack : RecipeDetailEvent
    data class OnCookingScreen(val recipeId: String) : RecipeDetailEvent
}