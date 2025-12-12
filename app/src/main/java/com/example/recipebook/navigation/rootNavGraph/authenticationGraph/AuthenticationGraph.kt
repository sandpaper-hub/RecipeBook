package com.example.recipebook.navigation.rootNavGraph.authenticationGraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
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
        composable(
            AuthenticationRoutes.Onboarding.route,
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeIn()
            }) {
            OnboardingScreen(
                onRegistrationScreen = { navController.navigate(AuthenticationRoutes.Registration.route) },
                onLoginScreen = { navController.navigate(AuthenticationRoutes.Login.route) }
            )
        }

        composable(
            AuthenticationRoutes.Login.route,
            enterTransition = {
                scaleIn(initialScale = 0f, animationSpec = tween(300)) + fadeIn()
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut()
            }
        ) {
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

        composable(
            AuthenticationRoutes.Registration.route,
            enterTransition = {
                scaleIn(initialScale = 0f, animationSpec = tween(200)) + fadeIn()
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(300)
                ) + fadeOut()
            }) {
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