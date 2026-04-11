package com.example.recipebook.domain.model.recipe.createRecipe

import com.example.recipebook.domain.model.ImageSourceType

data class UploadRecipeStepDraft(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val order: Int = 0,
    val imageSource: ImageSourceType = ImageSourceType.None
)