package com.example.recipebook.navigation.mainHomeGraph.recipesGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.mainHomeGraph.BottomNavigationItem
import com.example.recipebook.presentation.ui.recipesScreen.RecipesScreen
import com.example.recipebook.presentation.ui.uploadRecipeScreen.UploadRecipeScreen

fun NavGraphBuilder.recipesNavGraph(navController: NavController) {
    navigation(
        startDestination = RecipesRoutes.RecipesMain.route,
        route = BottomNavigationItem.Recipes.route
    ) {
        composable(RecipesRoutes.RecipesMain.route) {
            RecipesScreen(
                onUploadRecipeScreen = {
                    navController.navigate(RecipesRoutes.UploadRecipe.route)
                }
            )
        }

        composable(RecipesRoutes.UploadRecipe.route) {
            UploadRecipeScreen(
                onBackClicked = {
                    navController.popBackStack()
                }
            )
        }
    }
}