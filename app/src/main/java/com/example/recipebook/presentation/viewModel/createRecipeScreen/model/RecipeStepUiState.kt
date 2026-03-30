package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.FormField

data class RecipeStepUiState(
    val id: String = "",
    val title: String = "",
    val imageSource: String? = null,
    val stepDescription: FormField<String> = FormField("")
)