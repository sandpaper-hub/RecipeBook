package com.example.recipebook.navigation.mainHomeGraph.settingsGraph

sealed class SettingsRoutes(val route: String) {
    data object Settings: SettingsRoutes("settings")
    data object Account: SettingsRoutes(route = "account")
    data object Language: SettingsRoutes(route = "language")
    data object Theme: SettingsRoutes(route = "theme")
}