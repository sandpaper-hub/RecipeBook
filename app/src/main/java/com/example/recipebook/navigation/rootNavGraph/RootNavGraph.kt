package com.example.recipebook.navigation.rootNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.Routes
import com.example.recipebook.navigation.rootNavGraph.homeMainScreen.homeNavGraph
import com.example.recipebook.navigation.rootNavGraph.loginNavGraph.loginNavGraph
import com.example.recipebook.navigation.rootNavGraph.onBoardingScreen.onboardingNavGraph
import com.example.recipebook.navigation.rootNavGraph.registrationScreen.registrationNavGraph
import com.example.recipebook.navigation.rootNavGraph.splashScreen.splashNavGraph

@Composable
@Suppress("FunctionName")
fun RootNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Routes.Splash.route
    ) {
        splashNavGraph(
            onOnboardingScreen = {
                navController.navigate(Routes.Onboarding.route) {
                    popUpTo(Routes.Splash.route) { inclusive = true }
                }
            },
            onHomeScreen = {
                navController.navigate(Routes.MainHome.route) {
                    popUpTo(Routes.Splash.route) { inclusive = true }
                }
            })

        onboardingNavGraph(
            onRegistrationScreen = { navController.navigate(Routes.Registration.route) },
            onLoginScreen = { navController.navigate(Routes.Login.route) }
        )

        registrationNavGraph(onHomeScreen = {
            navController.navigate(Routes.MainHome.route) {
                popUpTo(Routes.Onboarding.route) { inclusive = true }
                launchSingleTop = true
            }
        })

        homeNavGraph()

        loginNavGraph(onMainHome = {
            navController.navigate(Routes.MainHome.route) {
                popUpTo(Routes.Onboarding.route) { inclusive = true }
                launchSingleTop = true
            }
        })
    }
}