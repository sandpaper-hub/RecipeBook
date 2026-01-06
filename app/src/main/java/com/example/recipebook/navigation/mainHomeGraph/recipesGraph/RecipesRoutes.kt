package com.example.recipebook.navigation.mainHomeGraph.recipesGraph

sealed class RecipesRoutes(val route: String){
    data object RecipesMain: RecipesRoutes("recipes_main")
    data object UploadRecipe: RecipesRoutes("upload_recipe")
}