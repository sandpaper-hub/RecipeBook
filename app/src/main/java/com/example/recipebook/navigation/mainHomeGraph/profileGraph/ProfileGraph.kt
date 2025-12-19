package com.example.recipebook.navigation.mainHomeGraph.profileGraph

import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.mainHomeGraph.BottomNavigationItem
import com.example.recipebook.navigation.mainHomeGraph.profileGraph.settingsGraph.settingsGraph
import com.example.recipebook.presentation.ui.editProfileScreen.EditProfileScreen
import com.example.recipebook.presentation.ui.profileScreen.ProfileScreen
import com.example.recipebook.presentation.viewModel.profileScreen.ProfileViewModel

fun NavGraphBuilder.profileNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = ProfileRoutes.ProfileMain.route,
        route = BottomNavigationItem.Profile.route
    ) {
        composable(ProfileRoutes.ProfileMain.route) { backStackEntry ->

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(BottomNavigationItem.Profile.route)
            }
            val viewModel: ProfileViewModel = hiltViewModel(parentEntry)

            ProfileScreen(
                viewModel = viewModel,
                onEditProfile = {
                    navController.navigate(ProfileRoutes.EditProfile.route)
                },
                onSettings = {
                    navController.navigate(Graph.SETTINGS)
                }
            )
        }

        composable(ProfileRoutes.EditProfile.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(BottomNavigationItem.Profile.route)
            }
            val viewModel: ProfileViewModel = hiltViewModel(parentEntry)
            EditProfileScreen(
                viewModel = viewModel,
                onBackNavigation = {
                    navController.popBackStack()
                }
            )
        }

        settingsGraph(navController = navController)
    }
}