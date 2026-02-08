package com.example.recipebook.domain.model.recipe.step

data class Step(
    val title: String = "",
    val description: String = "",
    val imageSource: String? = null
)