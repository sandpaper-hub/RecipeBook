package com.example.recipebook.presentation.ui.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.navigation.Auth
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.viewModel.splashScreen.SplashViewModel

@Composable
@Suppress("FunctionName")
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onHomeScreen: () -> Unit,
    onOnboardingScreen: () -> Unit
) {
    val destination = viewModel.startDestination.value

    LaunchedEffect(destination) {
        when(destination) {
            Graph.MAIN_HOME -> onHomeScreen()
            Auth.Onboarding.route -> onOnboardingScreen()
            else -> Unit
        }
    }
}