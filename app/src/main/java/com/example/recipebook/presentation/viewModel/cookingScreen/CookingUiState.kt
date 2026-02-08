package com.example.recipebook.presentation.viewModel.cookingScreen

data class CookingUiState(
    val recipeId: String = "",
    val recipeSteps: List<StepUiState> = listOf()
)
