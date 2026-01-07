package com.example.recipebook.data.mapper

import com.example.recipebook.data.dto.RecipeDto
import com.example.recipebook.domain.model.recipe.Recipe

fun Recipe.toDto(): RecipeDto {
    return RecipeDto(
        id = this.id,
        authorId = this.authorId,
        recipeName = this.recipeName,
        recipeDescription = this.recipeDescription,
        recipeTimeEstimation = this.recipeTimeEstimation,
        imageUrl = this.imageUrl,
        category = this.category,
        ingredients = this.ingredients,
        steps = this.steps
    )
}

fun RecipeDto.toDomain(): Recipe {
    return Recipe(
        id = this.id,
        authorId = this.authorId,
        recipeName = this.recipeName,
        recipeDescription = this.recipeDescription,
        recipeTimeEstimation = this.recipeTimeEstimation,
        imageUrl = this.imageUrl,
        category = this.category,
        ingredients = this.ingredients,
        steps = this.steps,
        createdAt = createdAt?.toDate()?.time ?: 0L
    )
}