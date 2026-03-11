package com.example.recipebook.presentation.viewModel.cookingScreen.model

data class CookingUiState(
    val recipeId: String = "",
    val recipeSteps: List<StepUiState> = listOf(),
    val isPagesMenuExpanded: Boolean = false
)
