package com.example.recipebook.data.mapper

import com.example.recipebook.data.dto.CollectionDto
import com.example.recipebook.domain.model.collection.UserCollection

fun UserCollection.toDto(): CollectionDto {
    return CollectionDto(
        id = this.id,
        name = this.name,
        description = this.description,
        recipeIds = this.recipeIds,
        imageUrl = this.imageSource
    )
}

fun CollectionDto.toDomain(): UserCollection {
    return UserCollection(
        id = this.id,
        name = this.name,
        description = this.description,
        imageSource = this.imageUrl,
        recipeIds =  this.recipeIds,
        createdAt = createdAt?.toDate()?.time ?: 0L
    )
}