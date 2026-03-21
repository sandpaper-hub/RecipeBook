package com.example.recipebook.domain.model

sealed interface ImageSourceType {
    data class Remote(val source: String): ImageSourceType
    data class Local(val source: String): ImageSourceType
    data object None: ImageSourceType
}