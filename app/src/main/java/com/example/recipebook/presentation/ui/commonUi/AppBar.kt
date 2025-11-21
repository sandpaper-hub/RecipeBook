package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipebook.navigation.mainHomeGraph.BottomNavigationItem
import com.example.recipebook.theme.DarkModeBodyColor
import com.example.recipebook.theme.GreenAccent

@Composable
@Suppress("FunctionName")
fun MainBottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Collaboration,
        BottomNavigationItem.Upload,
        BottomNavigationItem.Saved,
        BottomNavigationItem.Profile
    )
    val navigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navigationBackStackEntry?.destination

    NavigationBar {
        items.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label,
                        tint = if (selected) GreenAccent else DarkModeBodyColor
                    )
                }
            )
        }
    }
}