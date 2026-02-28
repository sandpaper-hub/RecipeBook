package com.example.recipebook.domain.model.recipe.step

data class EditStep(
    val id: String = "",
    val title: String = "",
    val order: Int = 0,
    val description: String = "",
    val sourceType: SourceType = SourceType.None
)
