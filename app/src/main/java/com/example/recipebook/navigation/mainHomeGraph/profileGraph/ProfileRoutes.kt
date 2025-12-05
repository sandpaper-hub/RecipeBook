package com.example.recipebook.navigation.mainHomeGraph.profileGraph

sealed class ProfileRoutes(val route: String){
    data object ProfileMain: ProfileRoutes("profile_main")
    data object EditProfile: ProfileRoutes("profile_edit")
}