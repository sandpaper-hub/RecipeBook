package com.example.recipebook.navigation.rootNavGraph.loginNavGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.recipebook.navigation.Routes
import com.example.recipebook.presentation.ui.loginScreen.LoginScreen

fun NavGraphBuilder.loginNavGraph(onMainHome: () -> Unit) {
    composable(Routes.Login.route) {
        LoginScreen(
            onMainHome = { onMainHome() }
        )
    }
}