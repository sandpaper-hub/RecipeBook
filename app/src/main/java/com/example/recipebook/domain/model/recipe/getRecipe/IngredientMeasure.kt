package com.example.recipebook.domain.model.recipe.getRecipe

enum class IngredientMeasure {
    TEASPOON,
    TABLESPOON,
    MILLILITER,
    LITER,
    GRAM,
    KILOGRAM,
    PCS,
    UNKNOWN;

    companion object {
        fun from(value: String?): IngredientMeasure =
            entries.find { it.name == value } ?: UNKNOWN
    }
}