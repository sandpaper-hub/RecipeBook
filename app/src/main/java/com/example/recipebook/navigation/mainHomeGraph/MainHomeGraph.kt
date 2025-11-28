package com.example.recipebook.navigation.mainHomeGraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun MainHomeGraph(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItem.Home.route,
        modifier = modifier
    ){
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
            composable(ProfileRoutes.ProfileMain.route) {
                ProfileScreen(
                    onEditProfile = {
                        navController.navigate(ProfileRoutes.EditProfile.route)
                    },
                    onSettings = {
                        navController.navigate(ProfileRoutes.Settings.route)
                    }
                )
            }

            composable(ProfileRoutes.EditProfile.route) {
                EditProfileScreen()
            }

            composable(ProfileRoutes.Settings.route){
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
        icon = R.drawable.home,
        label = "Home"
    )

    data object Collaboration : BottomNavigationItem(
        route = "collaboration",
        icon = R.drawable.collaboration,
        label = "Collaboration"
    )

    data object Upload : BottomNavigationItem(
        route = "upload",
        icon = R.drawable.upload_recipe,
        label = "Upload"
    )

    data object Saved : BottomNavigationItem(
        route = "saved",
        icon = R.drawable.saved,
        label = "Saved"
    )

    data object Profile : BottomNavigationItem(
        route = "profile",
        icon = R.drawable.profile,
        label = "Profile"
    )
}