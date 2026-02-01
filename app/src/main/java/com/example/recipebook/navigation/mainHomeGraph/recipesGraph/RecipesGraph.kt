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
            RecipesScreen(onRecipeDetail = { recipeId ->
                navController.navigate(RecipesRoutes.RecipeDetail.createRoute(recipeId))
            })
        }

        composable(
            route = "${RecipesRoutes.RecipeDetail.route}/{${RecipesRoutes.RecipeDetail.RECIPE_ID_ARG}}",
            arguments = listOf(navArgument(RecipesRoutes.RecipeDetail.RECIPE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            RecipeDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}