package com.example.recipebook.presentation.viewModel.model

import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory

data class RecipeUiState(
    val id: String = "",
    val imageSource: String? = null,
    val category: RecipeCategory = RecipeCategory.UNKNOWN,
    val name: String = "",
    val timeEstimationUiState: TimeEstimationUiState = TimeEstimationUiState(),
    val uploadedTime: Long = 0
)