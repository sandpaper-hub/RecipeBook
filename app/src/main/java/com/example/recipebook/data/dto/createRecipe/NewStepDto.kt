package com.example.recipebook.data.dto.createRecipe

data class NewStepDto(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val order: Int = 0,
    val imageUrl: String? = null
)
