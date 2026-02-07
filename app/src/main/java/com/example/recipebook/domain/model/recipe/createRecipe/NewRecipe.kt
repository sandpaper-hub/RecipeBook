package com.example.recipebook.domain.model.recipe.createRecipe

data class NewRecipe(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: String = "",
    val imageUrl: String? = null,
    val category: String = "",
    val ingredients: List<NewRecipeIngredient> = emptyList(),
    val createdAt: Long = 0L
)
