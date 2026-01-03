package com.example.recipebook.domain.model.recipe

data class Recipe(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: String = "",
    val imageUrl: String? = null,
    val category: String = "",
    val ingredients: List<RecipeIngredient> = emptyList(),
    val steps: List<RecipeStep> = emptyList(),
    val createdAt: Long = 0L
)
