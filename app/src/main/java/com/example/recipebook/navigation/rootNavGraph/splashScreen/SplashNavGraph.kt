package com.example.recipebook.navigation.rootNavGraph.splashScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.recipebook.navigation.Routes
import com.example.recipebook.presentation.ui.splashScreen.SplashScreen

fun NavGraphBuilder.splashNavGraph(
    onOnboardingScreen: () -> Unit,
    onHomeScreen: () -> Unit
) {
    composable(route = Routes.Splash.route) {
        SplashScreen(
            onHomeScreen = {
                onHomeScreen()

            },
            onOnboardingScreen = {
                onOnboardingScreen()
            }
        )
    }
}