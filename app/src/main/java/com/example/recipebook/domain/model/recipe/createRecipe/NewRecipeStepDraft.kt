package com.example.recipebook.domain.model.recipe.createRecipe

data class NewRecipeStepDraft(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val order: Int = 0,
    val imageSource: String? = null
)