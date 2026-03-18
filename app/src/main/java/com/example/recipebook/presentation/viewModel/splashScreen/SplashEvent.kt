package com.example.recipebook.presentation.viewModel.splashScreen

sealed interface SplashEvent {
    data object OnHome: SplashEvent
    data object OnOnboarding: SplashEvent
}