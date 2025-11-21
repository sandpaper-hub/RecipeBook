package com.example.recipebook.navigation.authenticationGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Auth
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.loginScreen.LoginScreen
import com.example.recipebook.presentation.ui.onboardingScreen.OnboardingScreen
import com.example.recipebook.presentation.ui.registrationScreen.RegistrationScreen

fun NavGraphBuilder.authenticationGraph(
    navController: NavController
) {
    navigation(
        route = Graph.AUTH,
        startDestination = Auth.Onboarding.route
    ) {
        composable(Auth.Onboarding.route) {
            OnboardingScreen(
                onRegistrationScreen = {navController.navigate(Auth.Registration.route)},
                onLoginScreen = {navController.navigate(Auth.Login.route)}
            )
        }

        composable(Auth.Login.route) {
            LoginScreen(
                onHomeScreen = {navController.navigate(Graph.MAIN_HOME){
                    popUpTo(Graph.AUTH) { inclusive = true }
                } }
            )
        }

        composable(Auth.Registration.route) {
            RegistrationScreen(
                onHomeScreen = {navController.navigate(Graph.MAIN_HOME) {
                    popUpTo(Graph.AUTH) {inclusive = true}
                } }
            )
        }
    }
}