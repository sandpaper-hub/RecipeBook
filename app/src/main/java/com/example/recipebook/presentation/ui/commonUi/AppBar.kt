package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
        BottomNavigationItem.UploadRecipe,
        BottomNavigationItem.Collection,
        BottomNavigationItem.Settings
    )
    val navigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navigationBackStackEntry?.destination
    var showSheet by rememberSaveable { mutableStateOf(false) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(66.dp)
    ) {
        items.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (item == BottomNavigationItem.UploadRecipe) {
                        showSheet = true
                    } else {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = GreenAccent,
                    selectedTextColor = GreenAccent,
                    selectedIndicatorColor = Color.Unspecified,
                    unselectedIconColor = DarkModeBodyColor,
                    unselectedTextColor = Color.Unspecified,
                    disabledIconColor = Color.Unspecified,
                    disabledTextColor = Color.Unspecified
                )
            )
        }
    }

    CreateBottomSheet(
        showSheet,
        onDismiss = { showSheet = false })
}