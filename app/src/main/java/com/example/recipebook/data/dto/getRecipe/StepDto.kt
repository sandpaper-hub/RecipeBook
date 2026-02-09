package com.example.recipebook.data.dto.getRecipe

data class StepDto(
    val title: String = "",
    val order: Int = 0,
    val description: String = "",
    val imageUrl: String? = null
)
