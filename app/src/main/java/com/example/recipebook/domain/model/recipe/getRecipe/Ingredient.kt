package com.example.recipebook.domain.model.recipe.getRecipe

data class Ingredient(
    val id: String = "",
    val value: String = "",
    val amount: String = "",
    val measure: IngredientMeasure = IngredientMeasure.UNKNOWN
)
