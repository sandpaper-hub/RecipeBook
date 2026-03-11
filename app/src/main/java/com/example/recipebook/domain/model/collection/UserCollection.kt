package com.example.recipebook.domain.model.collection

data class UserCollection(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val recipeIds: List<String> = listOf(),
    val imageSource: String? = null,
    val createdAt: Long = 0L
)
