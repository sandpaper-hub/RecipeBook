package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

data class RecipeStepUiState(
    val id: String = "",
    val title: String = "",
    val imageSource: String? = null,
    val stepDescription: String = ""
)