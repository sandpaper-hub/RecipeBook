package com.example.recipebook.navigation.rootNavGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.Graph
import com.example.recipebook.navigation.Root
import com.example.recipebook.navigation.rootNavGraph.authenticationGraph.authenticationGraph
import com.example.recipebook.presentation.ui.commonUi.RootScaffold
import com.example.recipebook.presentation.ui.mainScreenContainer.MainScreenContainer
import com.example.recipebook.presentation.ui.splashScreen.SplashScreen

@Composable
@Suppress("FunctionName")
fun RootNavGraph(navController: NavHostController = rememberNavController()) {
    RootScaffold { innerPadding ->
        NavHost(
            navController = navController,
            route = Graph.ROOT,
            startDestination = Root.Splash.route,
            modifier = Modifier.padding(innerPadding)
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
                MainScreenContainer(
                    onLogout = {
                        navController.navigate(Graph.AUTH) {
                            popUpTo(Graph.MAIN_HOME) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}