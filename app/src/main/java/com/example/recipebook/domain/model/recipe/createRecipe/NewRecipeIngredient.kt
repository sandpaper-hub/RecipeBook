package com.example.recipebook.domain.model.recipe.createRecipe

data class NewRecipeIngredient(
    val id: String = "",
    val value: String = "",
    val amount: String = "",
    val measure: String = ""
)