package com.example.recipebook.navigation.rootNavGraph

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
}

sealed class Root(val route: String){
    data object Splash: Root("splash")
}

sealed class Auth(val route: String){
    data object Onboarding: Auth("onboarding")
    data object Login: Auth("login")
    data object Registration: Auth("registration")
}

sealed class Home(val route: String) {
    data object MainHome: Home("main_screen_home_route")
}