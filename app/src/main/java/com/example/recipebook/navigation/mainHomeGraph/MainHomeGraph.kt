package com.example.recipebook.navigation.mainHomeGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipebook.R
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.CollectionDetailRoutes
import com.example.recipebook.navigation.mainHomeGraph.collectionDetailGraph.collectionDetailGraph
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.RecipeDetailRoutes
import com.example.recipebook.navigation.mainHomeGraph.recipeDetailGraph.recipeDetailNavGraph
import com.example.recipebook.navigation.mainHomeGraph.settingsGraph.settingsGraph
import com.example.recipebook.presentation.ui.searchScreen.SearchScreen
import com.example.recipebook.presentation.ui.collectionScreen.CollectionsScreen
import com.example.recipebook.presentation.ui.createCollectionScreen.CreateCollectionScreen
import com.example.recipebook.presentation.ui.createRecipeScreen.CreateRecipeScreen
import com.example.recipebook.presentation.ui.recipesScreen.RecipesScreen

@Composable
fun MainHomeGraph(
    navController: NavHostController,
    onLogout: () -> Unit,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Recipes.route,
        modifier = modifier
    ) {
        composable(BottomNavigationItem.Recipes.route) {
            RecipesScreen(onRecipeDetail = { recipeId ->
                navController.navigate(
                    RecipeDetailRoutes.RecipeDetail.createRoute(recipeId)
                )
            })
        }

        composable(BottomNavigationItem.Search.route) {
            SearchScreen(
                onRecipeDetail = { recipeId ->
                    navController.navigate(
                        RecipeDetailRoutes.RecipeDetail.createRoute(recipeId)
                    )
                }
            )
        }

        recipeDetailNavGraph(navController)

        composable(BottomNavigationItem.CreateRecipe.route) {
            CreateRecipeScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(BottomNavigationItem.CreateCollection.route) {
            CreateCollectionScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(BottomNavigationItem.Collections.route) {
            CollectionsScreen(
                onCollectionDetail = { collectionId ->
                    navController.navigate(
                        CollectionDetailRoutes.CollectionDetail.createRoute(collectionId)
                    )
                }
            )
        }

        collectionDetailGraph(navController)

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
    data object Recipes : BottomNavigationItem(
        route = "recipes",
        icon = R.drawable.home_icon,
        label = "Recipes"
    )

    data object Search : BottomNavigationItem(
        route = "search",
        icon = R.drawable.search_icon,
        label = "Search"
    )

    data object CreateRecipe : BottomNavigationItem(
        route = "create_recipe",
        icon = R.drawable.upload_recipe_icon,
        label = "Create recipe"
    )

    data object CreateCollection : BottomNavigationItem(
        route = "create_collection",
        icon = R.drawable.collection_icon,
        label = "Create collection"
    )

    data object Collections : BottomNavigationItem(
        route = "collections",
        icon = R.drawable.collection_icon,
        label = "Collections"
    )

    data object Settings : BottomNavigationItem(
        route = "settings",
        icon = R.drawable.settings_icon,
        label = "Settings"
    )
}