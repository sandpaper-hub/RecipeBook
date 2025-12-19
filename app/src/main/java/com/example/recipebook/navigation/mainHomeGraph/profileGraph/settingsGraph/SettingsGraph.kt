package com.example.recipebook.navigation.mainHomeGraph.profileGraph.settingsGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.LocalRootNavController
import com.example.recipebook.presentation.ui.accountScreen.AccountScreen
import com.example.recipebook.presentation.ui.languageScreen.LanguageScreen
import com.example.recipebook.presentation.ui.notificationScreen.NotificationScreen
import com.example.recipebook.presentation.ui.settingsScreen.SettingsScreen
import com.example.recipebook.presentation.ui.themeScreen.ThemeScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    navigation(
        route = Graph.SETTINGS,
        startDestination = SettingsRoutes.Settings.route
    ) {
        composable(SettingsRoutes.Settings.route) {
            val rootNavController = LocalRootNavController.current
            SettingsScreen(
                onBackNavigation = {
                    navController.popBackStack()
                },
                onAccountScreen = {
                    navController.navigate(SettingsRoutes.Account.route)
                },
                onNotificationScreen = {
                    navController.navigate(SettingsRoutes.Notifications.route)
                },
                onLanguageScreen = {
                    navController.navigate(SettingsRoutes.Language.route)
                },
                onThemeScreen = {
                    navController.navigate(SettingsRoutes.Theme.route)
                },
                onLogout = {
                    rootNavController?.let { root ->
                        root.navigate(Graph.AUTH) {
                            popUpTo(root.graph.findStartDestination().id) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                })
        }

        composable(SettingsRoutes.Account.route) {
            AccountScreen(onBackNavigation = {
                navController.popBackStack()
            })
        }

        composable(SettingsRoutes.Notifications.route) {
            NotificationScreen()
        }

        composable(SettingsRoutes.Language.route) {
            LanguageScreen(onBackNavigation = {
                navController.popBackStack()
            })
        }

        composable(SettingsRoutes.Theme.route) {
            ThemeScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}