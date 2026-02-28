package com.example.recipebook.presentation.viewModel.editRecipeScreen.model

data class EditRecipeStepUiState(
    val id: String = "",
    val title: String = "",
    val imageSource: ImageSource = ImageSource.None,
    val stepDescription: String = ""
)
