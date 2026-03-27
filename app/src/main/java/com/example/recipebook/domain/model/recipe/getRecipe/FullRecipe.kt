package com.example.recipebook.domain.model.recipe.getRecipe

import com.example.recipebook.domain.model.recipe.step.EditStep
import com.example.recipebook.domain.model.ImageSourceType
import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation

data class FullRecipe(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: NewTimeEstimation = NewTimeEstimation(),
    val imageSourceType: ImageSourceType = ImageSourceType.None,
    val category: RecipeCategory = RecipeCategory.UNKNOWN,
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<EditStep> = emptyList(),
)
