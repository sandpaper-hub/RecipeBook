package com.example.recipebook.data.mapper

import com.example.recipebook.data.dto.createRecipe.NewIngredientDto
import com.example.recipebook.data.dto.createRecipe.NewRecipeDto
import com.example.recipebook.data.dto.getRecipe.RecipeDto
import com.example.recipebook.domain.model.recipe.createRecipe.UploadRecipe
import com.example.recipebook.domain.model.recipe.getRecipe.Ingredient
import com.example.recipebook.domain.model.recipe.getRecipe.Recipe
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory

fun UploadRecipe.toDto(): NewRecipeDto {
    return NewRecipeDto(
        id = this.id,
        authorId = this.authorId,
        recipeName = this.recipeName,
        nameLowerCase = this.recipeName.lowercase(),
        recipeDescription = this.recipeDescription,
        recipeTimeEstimation = this.recipeNewTimeEstimation,
        imageUrl = this.imageUrl,
        category = this.category,
        ingredients = this.ingredients.map {
            NewIngredientDto(
                id = it.id,
                value = it.value,
                amount = it.amount,
                measure = it.measure
            )
        }
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
        category = RecipeCategory.from(this.category),
        collectionIds = this.collectionIds,
        ingredients = this.ingredients.map {
            Ingredient(
                id = it.id,
                value = it.value,
                amount = it.amount,
                measure = it.measure
            )
        },
        createdAt = createdAt?.toDate()?.time ?: 0L
    )
}