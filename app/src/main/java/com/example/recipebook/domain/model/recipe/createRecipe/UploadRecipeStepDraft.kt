package com.example.recipebook.domain.model.recipe.createRecipe

data class UploadRecipeStepDraft(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val order: Int = 0,
    val imageSource: String? = null
)