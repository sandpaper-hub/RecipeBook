package com.example.recipebook.navigation.rootNavGraph.authenticationGraph

sealed class AuthenticationRoutes(val route: String) {
    data object Onboarding : AuthenticationRoutes(route = "onboarding")
    data object Login : AuthenticationRoutes(route = "login")
    data object Registration : AuthenticationRoutes(route = "registration")
    data object PrivacyPolicy : AuthenticationRoutes(route = "privacy")
}