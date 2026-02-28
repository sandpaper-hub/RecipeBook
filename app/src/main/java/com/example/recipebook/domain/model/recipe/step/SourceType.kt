package com.example.recipebook.domain.model.recipe.step

sealed interface SourceType {
    data class Remote(val source: String): SourceType
    data class Local(val source: String): SourceType
    data object None: SourceType
}