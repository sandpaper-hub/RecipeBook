package com.example.recipebook.navigation.rootNavGraph.registrationScreen

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.recipebook.navigation.Routes
import com.example.recipebook.presentation.ui.registrationScreen.RegistrationScreen

fun NavGraphBuilder.registrationNavGraph(onHomeScreen: () -> Unit) {
    composable(Routes.Registration.route) {
        RegistrationScreen(
            onHomeScreen = { onHomeScreen() }
        )
    }
}