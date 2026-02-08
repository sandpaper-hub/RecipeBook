package com.example.recipebook.domain.model.recipe.createRecipe

data class NewRecipeStep(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String? = null
)
