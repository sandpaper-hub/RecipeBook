package com.example.recipebook.domain.model.recipe.getRecipe

data class Recipe(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: String = "",
    val imageUrl: String? = null,
    val category: RecipeCategory = RecipeCategory.UNKNOWN,
    val collectionIds: List<String> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val createdAt: Long = 0L
)
