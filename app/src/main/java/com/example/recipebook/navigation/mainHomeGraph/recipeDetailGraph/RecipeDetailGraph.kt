package com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.cooking_screen.CookingScreen
import com.example.recipebook.presentation.ui.editRecipeScreen.EditRecipeScreen
import com.example.recipebook.presentation.ui.recipeDetailScreen.RecipeDetailScreen

fun NavGraphBuilder.recipeDetailNavGraph(navController: NavController) {
    navigation(
        route = Graph.RECIPE_DETAIL,
        startDestination = "${RecipeDetailRoutes.RecipeDetail.route}/{${RecipeDetailRoutes.RecipeDetail.fullRoute}}"
    ) {
        composable(
            route = RecipeDetailRoutes.RecipeDetail.fullRoute,
            arguments = listOf(navArgument(RecipeDetailDestination.RECIPE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            RecipeDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onCookingScreen = { recipeId ->
                    navController.navigate(RecipeDetailRoutes.Cooking.createRoute(recipeId))
                },
                onRecipeEditScreen = { recipeId->
                    navController.navigate(RecipeDetailRoutes.EditRecipe.createRoute(recipeId))
                }
            )
        }

        composable(
            route = RecipeDetailRoutes.EditRecipe.fullRoute,
            arguments = listOf(navArgument(RecipeDetailDestination.RECIPE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            EditRecipeScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = RecipeDetailRoutes.Cooking.fullRoute,
            arguments = listOf(navArgument(RecipeDetailDestination.RECIPE_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            CookingScreen(onBack = {
                navController.popBackStack()
            })
        }
    }
}