package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem

data class IngredientUiState(
    val id: String = "",
    val value: String = "",
    val amount: String = "",
    val measure: MeasureMenuItem = MeasureMenuItem.NULL
)