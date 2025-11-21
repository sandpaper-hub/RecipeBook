package com.example.recipebook.navigation

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN_HOME = "main_home"
}

sealed class Root(val route: String){
    data object Splash: Root("splash")
}

sealed class Auth(val route: String) {
    data object Onboarding : Auth("onboarding")
    data object Login : Auth("login")
    data object Registration : Auth("registration")
}