package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.FormField
import com.example.recipebook.presentation.viewModel.model.ImageSource

data class RecipeStepUiState(
    val id: String = "",
    val title: FormField<String> = FormField(""),
    val imageSource: ImageSource = ImageSource.None,
    val description: FormField<String> = FormField("")
)