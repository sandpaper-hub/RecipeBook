package com.example.recipebook.data.mapper

import com.example.recipebook.data.dto.CollectionDto
import com.example.recipebook.domain.model.collection.UserCollection

fun UserCollection.toDto(): CollectionDto {
    return CollectionDto(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        recipesCount = this.recipesCount
    )
}

fun CollectionDto.toDomain(): UserCollection {
    return UserCollection(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        recipesCount = this.recipesCount,
        createdAt = createdAt?.toDate()?.time ?: 0L
    )
}