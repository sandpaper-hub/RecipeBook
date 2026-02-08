package com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.cooking_screen.CookingScreen
import com.example.recipebook.presentation.ui.recipeDetailScreen.RecipeDetailScreen

fun NavGraphBuilder.recipeDetailNavGraph(navController: NavController) {
    navigation(
        route = Graph.RECIPE_DETAIL,
        startDestination = "${RecipeDetailRoutes.RecipeDetail.route}/{${RecipeDetailRoutes.RecipeDetail.RECIPE_ID_ARG}}"
    ) {
        composable(
            route = "${RecipeDetailRoutes.RecipeDetail.route}/{${RecipeDetailRoutes.RecipeDetail.RECIPE_ID_ARG}}",
            arguments = listOf(navArgument(RecipeDetailRoutes.RecipeDetail.RECIPE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            RecipeDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onCookingScreen = { recipeId ->
                    navController.navigate(RecipeDetailRoutes.Cooking.createRoute(recipeId))
                }
            )
        }

        composable(
            route = "${RecipeDetailRoutes.Cooking.route}/{${RecipeDetailRoutes.Cooking.RECIPE_ID_ARG}}",
            arguments = listOf(navArgument(RecipeDetailRoutes.Cooking.RECIPE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            CookingScreen(onBack = {})
        }
    }
}