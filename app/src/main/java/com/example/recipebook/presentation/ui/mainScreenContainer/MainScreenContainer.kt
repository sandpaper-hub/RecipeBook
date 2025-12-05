package com.example.recipebook.presentation.ui.mainScreenContainer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.mainHomeGraph.BottomNavigationItem
import com.example.recipebook.navigation.mainHomeGraph.MainHomeGraph
import com.example.recipebook.navigation.mainHomeGraph.profileGraph.ProfileRoutes
import com.example.recipebook.presentation.ui.commonUi.MainBottomNavigationBar

@Composable
@Suppress("FunctionName")
fun MainScreenContainer() {
    val bottomNavController = rememberNavController()
    val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestinations = setOf(
        BottomNavigationItem.Home.route,
        BottomNavigationItem.Collaboration.route,
        BottomNavigationItem.Upload.route,
        BottomNavigationItem.Saved.route,
        ProfileRoutes.ProfileMain.route
    )

    val bottomBarVisibility = currentDestination?.route in bottomBarDestinations

    Scaffold(
        bottomBar = {
            if (bottomBarVisibility) {
                MainBottomNavigationBar(navController = bottomNavController)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainHomeGraph(
            navController = bottomNavController,
            Modifier.padding(innerPadding)
        )
    }
}