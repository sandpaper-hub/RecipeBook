package com.example.recipebook.data.dto

import com.example.recipebook.domain.model.recipe.RecipeIngredient
import com.example.recipebook.domain.model.recipe.RecipeStep
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
    val ingredients: List<RecipeIngredient> = emptyList(),
    val steps: List<RecipeStep> = emptyList(),
    @ServerTimestamp
    val createdAt: Timestamp? = null
)