package com.example.recipebook.data.dto

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class CollectionDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String? = null,
    val recipeIds: List<String> = listOf(),
    @ServerTimestamp
    val createdAt: Timestamp? = null
)
