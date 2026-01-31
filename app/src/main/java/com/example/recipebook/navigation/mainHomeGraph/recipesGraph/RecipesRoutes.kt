package com.example.recipebook.navigation.mainHomeGraph.recipesGraph

sealed class RecipesRoutes(val route: String) {
    data object RecipesMain : RecipesRoutes("recipes")
    data object RecipeDetail : RecipesRoutes("recipe_detail") {
        const val RECIPE_ID_ARG = "recipeId"

        fun createRoute(recipeId: String) =
            "$route/$recipeId"
    }
}