package com.example.recipebook.domain.model.recipe.step

import com.example.recipebook.domain.model.ImageSourceType

data class EditStep(
    val id: String = "",
    val title: String = "",
    val order: Int = 0,
    val description: String = "",
    val imageSourceType: ImageSourceType = ImageSourceType.None
)
