package com.example.recipebook.presentation.ui.splashScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.presentation.viewModel.splashScreen.SplashEvent
import com.example.recipebook.presentation.viewModel.splashScreen.SplashViewModel

@Composable
@Suppress("FunctionName")
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onHomeScreen: () -> Unit,
    onOnboardingScreen: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                SplashEvent.OnHome -> onHomeScreen()
                SplashEvent.OnOnboarding -> onOnboardingScreen()
            }
        }
    }
}