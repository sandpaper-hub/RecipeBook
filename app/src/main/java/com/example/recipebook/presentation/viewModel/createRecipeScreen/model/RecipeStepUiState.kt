package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

import com.example.recipebook.presentation.viewModel.model.Editable

data class RecipeStepUiState(
    val id: String = "",
    val title: String = "",
    val imageSource: String? = null,
    val stepDescription: Editable.StepDescription = Editable.StepDescription(
        stepId = id,
        description = ""
    )
)