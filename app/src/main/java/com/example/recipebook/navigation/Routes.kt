package com.example.recipebook.navigation

sealed class Routes(val route: String) {
    data object Onboarding: Routes("onboarding_route")
    data object Registration: Routes("registration_route")
}