package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

sealed interface RecipeDetailEvent {
    class GoBack() : RecipeDetailEvent
    data class OnCookingScreen(val recipeId: String) : RecipeDetailEvent
}