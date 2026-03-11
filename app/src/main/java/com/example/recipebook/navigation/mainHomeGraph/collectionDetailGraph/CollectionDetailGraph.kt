package com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailRoutes
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.recipeDetailNavGraph
import com.example.recipebook.presentation.ui.collectionDetailScreen.CollectionDetailScreen
import com.example.recipebook.presentation.ui.editCollectionScreen.CollectionEditScreen

fun NavGraphBuilder.collectionDetailGraph(navController: NavController) {
    navigation(
        route = Graph.COLLECTION_DETAIL,
        startDestination = "${CollectionDetailRoutes.CollectionDetail.route}/{${CollectionDetailRoutes.CollectionDetail.fullRoute}"
    ) {
        composable(
            route = CollectionDetailRoutes.CollectionDetail.fullRoute,
            arguments = listOf(navArgument(CollectionDetailDestination.COLLECTION_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            CollectionDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onRecipeDetail = { recipeId ->
                    navController.navigate(
                        RecipeDetailRoutes.RecipeDetail.createRoute(recipeId)
                    )
                },
                onCollectionEdit = { collectionId ->
                    navController.navigate(
                        CollectionDetailRoutes.CollectionEdit.createRoute(collectionId)
                    )
                }
            )
        }

        composable(
            route = CollectionDetailRoutes.CollectionEdit.fullRoute,
            arguments = listOf(navArgument(CollectionDetailDestination.COLLECTION_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            CollectionEditScreen(
                onBack = { navController.popBackStack() }
            )
        }

        recipeDetailNavGraph(navController)
    }
}