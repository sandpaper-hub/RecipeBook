package com.example.recipebook.navigation.mainHomeGraph.profileGraph.settingsGraph

sealed class SettingsRoutes(val route: String) {
    data object Settings: SettingsRoutes("settings")
    data object Account: SettingsRoutes(route = "account")
    data object Notifications: SettingsRoutes(route = "notification")
    data object Language: SettingsRoutes(route = "language")
    data object Theme: SettingsRoutes(route = "theme")
}