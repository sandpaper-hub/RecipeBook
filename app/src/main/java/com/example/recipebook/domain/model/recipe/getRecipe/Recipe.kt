package com.example.recipebook.domain.model.recipe.getRecipe

import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation

data class Recipe(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: NewTimeEstimation = NewTimeEstimation(),
    val imageUrl: String? = null,
    val category: RecipeCategory = RecipeCategory.UNKNOWN,
    val collectionIds: List<String> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val createdAt: Long = 0L
)