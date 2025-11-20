package com.example.recipebook.navigation

sealed class Routes(val route: String) {
    data object Onboarding: Routes("onboarding_route")
    data object Registration: Routes("registration_route")
    data object MainHome: Routes("main_screen_home_route")
    data object Login: Routes("login_route")
    data object Splash: Routes("splash_route")
}