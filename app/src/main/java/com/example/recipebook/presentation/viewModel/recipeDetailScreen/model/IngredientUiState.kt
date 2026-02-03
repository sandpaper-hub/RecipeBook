package com.example.recipebook.presentation.viewModel.recipeDetailScreen.model

import androidx.annotation.StringRes

data class IngredientUiState(
    val id: String = "",
    val value: String = "",
    val amount: String = "",
    @StringRes val measure: Int = 0
)
