package com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.recipeDetailScreen.RecipeDetailScreen

fun NavGraphBuilder.recipeDetailGraph(
    navController: NavController
) {
    navigation(
        route = "${Graph.RECIPE_DETAIL}/{recipeId}",
        startDestination = "${RecipeDetailRoutes.RecipeDetail.route}/{recipeId}"
    ) {
        composable(
            route = "${RecipeDetailRoutes.RecipeDetail.route}/{recipeId}",
            listOf(
                navArgument("recipeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId")!!
            RecipeDetailScreen(recipeId = recipeId)
        }

        composable(RecipeDetailRoutes.Cooking.route) {

        }

        composable(RecipeDetailRoutes.CompleteCooking.route) {

        }
    }
}