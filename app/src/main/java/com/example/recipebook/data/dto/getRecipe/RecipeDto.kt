package com.example.recipebook.data.dto.getRecipe

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class RecipeDto(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: String = "",
    val imageUrl: String? = null,
    val category: String = "",
    val ingredients: List<IngredientDto> = emptyList(),
    @field:ServerTimestamp
    val createdAt: Timestamp? = null
)