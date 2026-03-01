package com.example.recipebook.domain.model.collection

import com.example.recipebook.domain.model.recipe.step.ImageSourceType

data class UserCollectionEdit(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val recipeIds: List<String> = listOf(),
    val imageSource: ImageSourceType = ImageSourceType.None,
    val createdAt: Long = 0L
)
