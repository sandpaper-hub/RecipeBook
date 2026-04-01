package com.example.recipebook.presentation.viewModel.createRecipeScreen.model

sealed interface CreateRecipeEvent {
    data object MinIngredientCountLimit: CreateRecipeEvent
    data object MaxIngredientCountLimit: CreateRecipeEvent
}