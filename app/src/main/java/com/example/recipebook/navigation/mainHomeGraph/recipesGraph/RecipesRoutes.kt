package com.example.recipebook.navigation.mainHomeGraph.recipesGraph

sealed class RecipesRoutes(val route: String){
    data object RecipesMain: RecipesRoutes("recipes")
}