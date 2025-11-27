package com.example.recipebook.navigation.mainHomeGraph

sealed class ProfileRoutes(val route: String){
    data object ProfileMain: ProfileRoutes("profile_main")
    data object EditProfile: ProfileRoutes("profile_edit")
    data object Settings: ProfileRoutes("profile_settings")
}