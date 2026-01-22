package com.example.recipebook.navigation.mainHomeGraph.settingsGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.recipebook.navigation.Graph
import com.example.recipebook.presentation.ui.accountScreen.AccountScreen
import com.example.recipebook.presentation.ui.languageScreen.LanguageScreen
import com.example.recipebook.presentation.ui.settingsScreen.SettingsScreen
import com.example.recipebook.presentation.ui.themeScreen.ThemeScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavController,
    onLogout: () -> Unit
) {
    navigation(
        route = Graph.SETTINGS,
        startDestination = SettingsRoutes.Settings.route
    ) {
        composable(SettingsRoutes.Settings.route) {
            SettingsScreen(
                onAccountScreen = {
                    navController.navigate(SettingsRoutes.Account.route)
                },
                onLanguageScreen = {
                    navController.navigate(SettingsRoutes.Language.route)
                },
                onThemeScreen = {
                    navController.navigate(SettingsRoutes.Theme.route)
                },
                onLogout = onLogout)
        }

        composable(SettingsRoutes.Account.route) {
            AccountScreen(onBackNavigation = {
                navController.popBackStack()
            })
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