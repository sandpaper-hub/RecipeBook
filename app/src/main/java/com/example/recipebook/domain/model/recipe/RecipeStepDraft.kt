package com.example.recipebook.domain.model.recipe

data class RecipeStepDraft(
    val id: String = "",
    val description: String = "",
    val imageSource: String? = null
)