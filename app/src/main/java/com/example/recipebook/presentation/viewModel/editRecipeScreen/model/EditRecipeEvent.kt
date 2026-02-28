package com.example.recipebook.presentation.viewModel.editRecipeScreen.model

sealed interface EditRecipeEvent {
    object GoBack : EditRecipeEvent
}