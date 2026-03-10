package com.example.recipebook.data.dto.createRecipe

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class NewRecipeDto(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val nameLowerCase: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: String = "",
    val imageUrl: String? = null,
    val category: String = "",
    val ingredients: List<NewIngredientDto> = emptyList(),
    @ServerTimestamp
    val createdAt: Timestamp? = null
)