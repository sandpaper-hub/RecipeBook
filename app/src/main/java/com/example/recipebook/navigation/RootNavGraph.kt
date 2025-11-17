package com.example.recipebook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.presentation.ui.HomeScreen.HomeScreen
import com.example.recipebook.presentation.ui.registrationScreen.RegistrationScreen
import com.example.recipebook.presentation.ui.onboardingScreen.OnboardingScreen

@Composable
@Suppress("FunctionName")
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.Onboarding.route
    ) {
        composable(route = Routes.Onboarding.route) {
            OnboardingScreen(
                onOpenRegistrationScreen = { navController.navigate(Routes.Registration.route) }
            )
        }

        composable(route = Routes.Registration.route) {
            RegistrationScreen(
                onHomeScreen = {navController.navigate(Routes.Home.route)}
            )
        }

        composable(route = Routes.Home.route){
            HomeScreen()
        }
    }
}