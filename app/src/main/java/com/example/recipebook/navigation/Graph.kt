package com.example.recipebook.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

object Graph {
    const val ROOT = "root_graph"
    const val AUTH = "auth_graph"
    const val MAIN_HOME = "main_home"
    const val SETTINGS = "settings_graph"
}

sealed class Root(val route: String) {
    data object Splash : Root("splash")
}

val LocalRootNavController = staticCompositionLocalOf<NavHostController?> { null }