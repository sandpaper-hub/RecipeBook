package com.example.recipebook.navigation.mainHomeGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.recipebook.R
import com.example.recipebook.navigation.mainHomeGraph.profileGraph.profileNavGraph
import com.example.recipebook.navigation.rootNavGraph.authenticationGraph.authenticationGraph
import com.example.recipebook.presentation.ui.mainHomeScreen.MainHomeScreen
import com.example.recipebook.presentation.ui.uploadScreen.UploadScreen
import com.example.recipebook.presentation.ui.collaborationScreen.CollaborationScreen
import com.example.recipebook.presentation.ui.savedScreen.SavedScreen

@Composable
fun MainHomeGraph(
    navController: NavHostController,
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

        composable(BottomNavigationItem.Collaboration.route) {
            CollaborationScreen()
        }

        composable(BottomNavigationItem.Upload.route) {
            UploadScreen()
        }

        composable(BottomNavigationItem.Saved.route) {
            SavedScreen()
        }

        profileNavGraph(navController)

        authenticationGraph(navController)
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

    data object Collaboration : BottomNavigationItem(
        route = "collaboration",
        icon = R.drawable.collaboration_icon,
        label = "Collaboration"
    )

    data object Upload : BottomNavigationItem(
        route = "upload",
        icon = R.drawable.upload_recipe_icon,
        label = "Upload"
    )

    data object Saved : BottomNavigationItem(
        route = "saved",
        icon = R.drawable.saved_icon,
        label = "Saved"
    )

    data object Profile : BottomNavigationItem(
        route = "profile",
        icon = R.drawable.profile_icon,
        label = "Profile"
    )
}