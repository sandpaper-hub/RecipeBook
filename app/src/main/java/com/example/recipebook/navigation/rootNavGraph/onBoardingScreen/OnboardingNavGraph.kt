package com.example.recipebook.navigation.rootNavGraph.onBoardingScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.recipebook.navigation.Routes
import com.example.recipebook.presentation.ui.onboardingScreen.OnboardingScreen

fun NavGraphBuilder.onboardingNavGraph(
    onRegistrationScreen: () -> Unit,
    onLoginScreen: () -> Unit
) {
    composable(Routes.Onboarding.route) {
        OnboardingScreen(
            onRegistrationScreen = { onRegistrationScreen() },
            onLoginScreen = { onLoginScreen() }
        )
    }
}