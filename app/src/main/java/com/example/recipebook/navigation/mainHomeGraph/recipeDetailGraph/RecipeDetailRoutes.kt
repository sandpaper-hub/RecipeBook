package com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph

sealed class RecipeDetailRoutes(val route: String) {

    val fullRoute: String
        get() = "$route/{${RecipeDetailDestination.RECIPE_ID_ARG}}"

    fun createRoute(recipeId: String) =
        "$route/$recipeId"

    data object RecipeDetail : RecipeDetailRoutes(RecipeDetailDestination.RECIPE_DETAIL_ROUTE)

    data object Cooking : RecipeDetailRoutes(RecipeDetailDestination.COOKING_ROUTE)
}

object RecipeDetailDestination {
    const val RECIPE_DETAIL_ROUTE = "recipe_detail"
    const val COOKING_ROUTE = "cooking"
    const val RECIPE_ID_ARG = "recipeId"
}