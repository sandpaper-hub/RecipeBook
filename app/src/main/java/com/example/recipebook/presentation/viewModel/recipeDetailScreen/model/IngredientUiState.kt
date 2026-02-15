package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

import com.example.recipebook.domain.model.recipe.getRecipe.IngredientMeasure

data class IngredientUiState(
    val id: String = "",
    val value: String = "",
    val amount: String = "",
    val measure: IngredientMeasure = IngredientMeasure.UNKNOWN
)
