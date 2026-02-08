package com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph

sealed class RecipeDetailRoutes(val route: String) {
    data object RecipeDetail : RecipeDetailRoutes("recipe_detail") {
        const val RECIPE_ID_ARG = "recipeId"

        fun createRoute(recipeId: String) =
            "$route/$recipeId"
    }

    data object Cooking : RecipeDetailRoutes("cooking") {
        const val RECIPE_ID_ARG = "recipeId"

        fun createRoute(recipeId: String) =
            "$route/$recipeId"
    }
}