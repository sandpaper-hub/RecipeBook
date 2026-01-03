package com.example.recipebook.navigation.mainHomeGraph.profileGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.mainHomeGraph.BottomNavigationItem
import com.example.recipebook.navigation.mainHomeGraph.profileGraph.settingsGraph.settingsGraph
import com.example.recipebook.presentation.ui.editProfileScreen.EditProfileScreen
import com.example.recipebook.presentation.ui.profileScreen.ProfileScreen

fun NavGraphBuilder.profileNavGraph(
    navController: NavController,
    onLogout: () -> Unit
) {
    navigation(
        startDestination = ProfileRoutes.ProfileMain.route,
        route = BottomNavigationItem.Profile.route
    ) {
        composable(ProfileRoutes.ProfileMain.route) {
            ProfileScreen(
                onEditProfile = {
                    navController.navigate(ProfileRoutes.EditProfile.route)
                },
                onSettings = {
                    navController.navigate(Graph.SETTINGS)
                }
            )
        }

        composable(ProfileRoutes.EditProfile.route) {
            EditProfileScreen(
                onBackNavigation = {
                    navController.popBackStack()
                }
            )
        }

        settingsGraph(
            navController = navController,
            onLogout = onLogout
        )
    }
}