package com.example.recipebook.navigation.rootNavGraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.rootNavGraph.authenticationGraph.authenticationGraph

import com.example.recipebook.presentation.ui.mainScreenHome.MainHomeScreen
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
                    navController.navigate(Home.MainHome.route) {
                        popUpTo(Root.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        authenticationGraph(navController = navController)

        composable(Home.MainHome.route) {
            MainHomeScreen()
        }
    }
}