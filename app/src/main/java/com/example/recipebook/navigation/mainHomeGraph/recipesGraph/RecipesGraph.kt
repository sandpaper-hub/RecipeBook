package com.example.recipebook.navigation.mainHomeGraph.recipesGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.recipesScreen.RecipesScreen

fun NavGraphBuilder.recipesNavGraph(navController: NavController) {
    navigation(
        route = Graph.COLLECTION,
        startDestination = RecipesRoutes.RecipesMain.route
    ) {
        composable(RecipesRoutes.RecipesMain.route) {
            RecipesScreen()
        }
    }
}