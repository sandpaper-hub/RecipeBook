package com.example.recipebook.navigation.mainHomeGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipebook.R
import com.example.recipebook.navigation.mainHomeGraph.settingsGraph.settingsGraph
import com.example.recipebook.navigation.mainHomeGraph.recipesGraph.recipesNavGraph
import com.example.recipebook.presentation.ui.mainHomeScreen.MainHomeScreen
import com.example.recipebook.presentation.ui.collaborationScreen.CollaborationScreen
import com.example.recipebook.presentation.ui.createCollectionScreen.CreateCollectionScreen
import com.example.recipebook.presentation.ui.createRecipeScreen.CreateRecipeScreen

@Composable
fun MainHomeGraph(
    navController: NavHostController,
    onLogout: () -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavigationItem.Home.route) {
            MainHomeScreen()
        }

        composable(BottomNavigationItem.Collection.route) {
            CollaborationScreen()
        }

        composable(BottomNavigationItem.CreateRecipe.route) {
            CreateRecipeScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(BottomNavigationItem.CreateCollection.route){
            CreateCollectionScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        recipesNavGraph(navController = navController)

        settingsGraph(
            navController = navController,
            onLogout = onLogout
        )
    }
}

sealed class BottomNavigationItem(
    val route: String,
    val icon: Int,
    val label: String
) {
    data object Home : BottomNavigationItem(
        route = "home",
        icon = R.drawable.home_icon,
        label = "Home"
    )

    data object Recipes : BottomNavigationItem(
        route = "recipes",
        icon = R.drawable.cook_hat_icon,
        label = "Recipes"
    )

    data object CreateRecipe : BottomNavigationItem(
        route = "create_recipe",
        icon = R.drawable.upload_recipe_icon,
        label = "Create recipe"
    )

    data object CreateCollection: BottomNavigationItem(
        route = "create_collection",
        icon = R.drawable.collection_icon,
        label = "Create collection"
    )

    data object Collection : BottomNavigationItem(
        route = "collection",
        icon = R.drawable.collection_icon,
        label = "Collection"
    )

    data object Settings : BottomNavigationItem(
        route = "settings",
        icon = R.drawable.settings_icon,
        label = "Settings"
    )
}