package com.example.recipebook.data.dto.createRecipe

import com.example.recipebook.domain.model.recipe.createRecipe.NewTimeEstimation
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class NewRecipeDto(
    val id: String = "",
    val authorId: String = "",
    val recipeName: String = "",
    val nameLowerCase: String = "",
    val recipeDescription: String = "",
    val recipeTimeEstimation: NewTimeEstimation = NewTimeEstimation(),
    val imageUrl: String? = null,
    val category: String = "",
    val ingredients: List<NewIngredientDto> = emptyList(),
    @ServerTimestamp
    val createdAt: Timestamp? = null
)