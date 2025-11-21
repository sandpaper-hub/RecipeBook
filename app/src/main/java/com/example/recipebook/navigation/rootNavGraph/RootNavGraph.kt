package com.example.recipebook.navigation.rootNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.Root
import com.example.recipebook.navigation.authenticationGraph.authenticationGraph

import com.example.recipebook.presentation.ui.mainScreenContainer.MainScreenContainer
import com.example.recipebook.presentation.ui.splashScreen.SplashScreen

@Composable
@Suppress("FunctionName")
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Root.Splash.route
    ) {
        composable(Root.Splash.route) {
            SplashScreen(
                onOnboardingScreen = {
                    navController.navigate(Graph.AUTH) {
                        popUpTo(Root.Splash.route) { inclusive = true }
                    }
                }, onHomeScreen = {
                    navController.navigate(Graph.MAIN_HOME) {
                        popUpTo(Root.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        authenticationGraph(navController = navController)

        composable(Graph.MAIN_HOME) {
            MainScreenContainer()
        }
    }
}