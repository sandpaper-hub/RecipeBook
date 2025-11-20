package com.example.recipebook.navigation.rootNavGraph.homeMainScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.recipebook.navigation.Routes
import com.example.recipebook.presentation.ui.mainScreenHome.MainHomeScreen

fun NavGraphBuilder.homeNavGraph() {
    composable(Routes.MainHome.route) {
        MainHomeScreen()
    }
}