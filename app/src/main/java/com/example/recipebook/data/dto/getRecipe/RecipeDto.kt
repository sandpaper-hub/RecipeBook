package com.example.recipebook.data.dto.getRecipe

import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class RecipeDto(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: NewTimeEstimation = NewTimeEstimation(),
    val imageUrl: String? = null,
    val category: String = "",
    val collectionIds: List<String> = emptyList(),
    val ingredients: List<IngredientDto> = emptyList(),
    @field:ServerTimestamp
    val createdAt: Timestamp? = null
)