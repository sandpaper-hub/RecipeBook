package com.example.recipebook.domain.model.recipe.step

sealed interface ImageSourceType {
    data class Remote(val source: String): ImageSourceType
    data class Local(val source: String): ImageSourceType
    data object None: ImageSourceType
}