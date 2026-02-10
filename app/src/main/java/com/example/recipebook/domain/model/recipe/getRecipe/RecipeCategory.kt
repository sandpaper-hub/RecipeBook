package com.example.recipebook.domain.model.recipe.getRecipe

enum class RecipeCategory {
    APPETIZER,
    SALAD,
    SOUP,
    MAIN,
    GARNISH,
    SAUCE,
    DESERT,
    DRINK,
    UNKNOWN;

    companion object{
        fun from(value: String?): RecipeCategory =
            entries.find { it.name == value } ?: UNKNOWN
    }
}