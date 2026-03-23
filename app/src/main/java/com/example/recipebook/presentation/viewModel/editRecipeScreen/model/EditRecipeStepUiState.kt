package com.example.recipebook.presentation.viewModel.editRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.Editable
import com.example.recipebook.presentation.viewModel.model.ImageSource

data class EditRecipeStepUiState(
    val id: String = "",
    val title: String = "",
    val imageSource: ImageSource = ImageSource.None,
    val stepDescription: Editable.StepDescription = Editable.StepDescription(stepId = id, description = "")
)
