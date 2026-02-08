package com.example.recipebook.domain.model.recipe.createRecipe

data class NewRecipeStep(
    val id: String = "",
    val title: String = "",
    val order: Int = 0,
    val description: String = "",
    val imageUrl: String? = null
)
