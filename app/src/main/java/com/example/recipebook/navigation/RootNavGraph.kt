package com.example.recipebook.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.presentation.ui.homeScreen.HomeScreen
import com.example.recipebook.presentation.ui.loginScreen.LoginScreen
import com.example.recipebook.presentation.ui.registrationScreen.RegistrationScreen
import com.example.recipebook.presentation.ui.onboardingScreen.OnboardingScreen
import com.example.recipebook.presentation.ui.splashScreen.SplashScreen

@Composable
@Suppress("FunctionName")
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.Splash.route
    ) {
        composable(
            route = Routes.Splash.route,
            exitTransition = { fadeOut(tween(200)) }) {
            SplashScreen(
                onHomeScreen = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                },
                onOnboardingScreen = {
                    navController.navigate(Routes.Onboarding.route) {
                        popUpTo(Routes.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Routes.Onboarding.route) {
            OnboardingScreen(
                onOpenRegistrationScreen = { navController.navigate(Routes.Registration.route) },
                onOpenLoginScreen = { navController.navigate(Routes.Login.route) }
            )
        }

        composable(route = Routes.Registration.route) {
            RegistrationScreen(
                onHomeScreen = { navController.navigate(Routes.Home.route) }
            )
        }

        composable(route = Routes.Home.route) {
            HomeScreen()
        }

        composable(route = Routes.Login.route) {
            LoginScreen(
                onHomeScreen = { navController.navigate(Routes.Home.route) })
        }
    }
}