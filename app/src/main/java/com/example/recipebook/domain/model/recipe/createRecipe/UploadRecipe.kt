package com.example.recipebook.domain.model.recipe.createRecipe

data class UploadRecipe(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeNewTimeEstimation: NewTimeEstimation = NewTimeEstimation(),
    val imageUrl: String? = null,
    val category: String = "",
    val ingredients: List<NewRecipeIngredient> = emptyList(),
    val createdAt: Long = 0L
)
