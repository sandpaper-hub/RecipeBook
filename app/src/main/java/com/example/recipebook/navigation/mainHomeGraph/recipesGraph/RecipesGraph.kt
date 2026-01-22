package com.example.recipebook.navigation.mainHomeGraph.recipesGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.recipeDetailScreen.RecipeDetailScreen
import com.example.recipebook.presentation.ui.recipesScreen.RecipesScreen

fun NavGraphBuilder.recipesNavGraph(navController: NavController) {
    navigation(
        route = Graph.COLLECTION,
        startDestination = RecipesRoutes.RecipesMain.route
    ) {
        composable(RecipesRoutes.RecipesMain.route) {
            RecipesScreen(onRecipeDetail = {
                navController.navigate("${RecipesRoutes.RecipeDetail.route}/{recipeId}")
            })
        }

        composable(
            route = "${RecipesRoutes.RecipeDetail.route}/{recipeId}",
            listOf(navArgument("recipeId") { type = NavType.StringType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")!!
            RecipeDetailScreen(recipeId = recipeId)
        }
    }
}