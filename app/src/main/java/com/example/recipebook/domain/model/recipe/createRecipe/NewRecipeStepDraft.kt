package com.example.recipebook.domain.model.recipe.createRecipe

data class NewRecipeStepDraft(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageSource: String? = null
)