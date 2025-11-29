package com.example.recipebook.navigation.mainHomeGraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.mainHomeScreen.MainHomeScreen
import com.example.recipebook.presentation.ui.uploadScreen.UploadScreen
import com.example.recipebook.presentation.ui.collaborationScreen.CollaborationScreen
import com.example.recipebook.presentation.ui.editProfileScreen.EditProfileScreen
import com.example.recipebook.presentation.ui.profileScreen.ProfileScreen
import com.example.recipebook.presentation.ui.savedScreen.SavedScreen
import com.example.recipebook.presentation.ui.settingsScreen.SettingsScreen
import com.example.recipebook.presentation.viewModel.profileScreen.ProfileViewModel

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
                        navController.navigate(ProfileRoutes.Settings.route)
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

            composable(ProfileRoutes.Settings.route) {
                SettingsScreen()
            }
        }
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