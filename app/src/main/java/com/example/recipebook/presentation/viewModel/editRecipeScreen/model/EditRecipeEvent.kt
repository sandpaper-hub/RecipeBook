package com.example.recipebook.presentation.viewModel.editRecipeScreen.model

sealed interface EditRecipeEvent {
    object GoBack : EditRecipeEvent
    object MinIngredientCountLimit: EditRecipeEvent
    object MaxIngredientCountLimit: EditRecipeEvent
    object MinStepsCountLimit: EditRecipeEvent
    object MaxStepsCountLimit: EditRecipeEvent

}