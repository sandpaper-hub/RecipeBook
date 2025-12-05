package com.example.recipebook.navigation.rootNavGraph.authenticationGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.loginScreen.LoginScreen
import com.example.recipebook.presentation.ui.onboardingScreen.OnboardingScreen
import com.example.recipebook.presentation.ui.privacyScreen.PrivacyPolicyScreen
import com.example.recipebook.presentation.ui.registrationScreen.RegistrationScreen

fun NavGraphBuilder.authenticationGraph(
    navController: NavController
) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthenticationRoutes.Onboarding.route
    ) {
        composable(AuthenticationRoutes.Onboarding.route) {
            OnboardingScreen(
                onRegistrationScreen = { navController.navigate(AuthenticationRoutes.Registration.route) },
                onLoginScreen = { navController.navigate(AuthenticationRoutes.Login.route) }
            )
        }

        composable(AuthenticationRoutes.Login.route) {
            LoginScreen(
                onHomeScreen = {
                    navController.navigate(Graph.MAIN_HOME) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                },
                onRegistrationScreen = {
                    navController.navigate(AuthenticationRoutes.Registration.route) {
                        popUpTo(AuthenticationRoutes.Onboarding.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AuthenticationRoutes.Registration.route) {
            RegistrationScreen(
                onHomeScreen = {
                    navController.navigate(Graph.MAIN_HOME) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                },
                onLoginScreen = {
                    navController.navigate(AuthenticationRoutes.Login.route) {
                        popUpTo(AuthenticationRoutes.Onboarding.route) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onPrivacyScreen = {
                    navController.navigate(AuthenticationRoutes.PrivacyPolicy.route)
                }
            )
        }

        composable(AuthenticationRoutes.PrivacyPolicy.route) {
            PrivacyPolicyScreen()
        }
    }
}