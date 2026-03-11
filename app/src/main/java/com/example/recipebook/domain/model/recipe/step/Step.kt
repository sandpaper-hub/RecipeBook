package com.example.recipebook.domain.model.recipe.step

data class Step(
    val id: String = "",
    val title: String = "",
    val order: Int = 0,
    val description: String = "",
    val imageSource: String? = null
)