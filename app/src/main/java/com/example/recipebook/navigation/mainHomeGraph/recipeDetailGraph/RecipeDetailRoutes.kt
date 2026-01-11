package com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph

sealed class RecipeDetailRoutes(
    val route: String
) {
    data object RecipeDetail: RecipeDetailRoutes("recipe_detail")
    data object Cooking: RecipeDetailRoutes("cooking")
    data object CompleteCooking: RecipeDetailRoutes("complete_cooking")
}