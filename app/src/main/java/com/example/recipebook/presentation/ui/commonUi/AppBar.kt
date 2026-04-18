package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.recipebook.R
import com.example.recipebook.navigation.mainHomeGraph.BottomNavigationItem
import com.example.recipebook.theme.DarkModeBodyColor
import com.example.recipebook.theme.GreenAccent

@Composable
@Suppress("FunctionName")
fun MainBottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Recipes,
        BottomNavigationItem.Search,
        BottomNavigationItem.CreateRecipe,
        BottomNavigationItem.Collections,
        BottomNavigationItem.Settings
    )
    val navigationBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navigationBackStackEntry?.destination
    var showSheet by rememberSaveable { mutableStateOf(false) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEach { item ->
            val selected = currentDestination
                ?.hierarchy
                ?.any { it.route == item.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (item == BottomNavigationItem.CreateRecipe) {
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
        onDismiss = { showSheet = false },
        onCreateRecipeScreen = {
            navController.navigate(BottomNavigationItem.CreateRecipe.route)
            showSheet = false
        },
        onCreateCollectionScreen = {
            navController.navigate(BottomNavigationItem.CreateCollection.route)
            showSheet = false
        })
}

@Composable
@Suppress("FunctionName")
fun TopBarMoreAction(
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Spacer(modifier = Modifier.width(24.dp))

        ClickableIcon(
            painter = painterResource(R.drawable.back_arrow_icon),
            contentDescription = stringResource(R.string.back_button),
            onClick = onBackClick
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.BottomCenter) {
            ClickableIcon(
                painter = painterResource(R.drawable.more_vert_icon),
                contentDescription = stringResource(R.string.more_action_button),
                modifier = Modifier,
                onClick = onMenuClick
            )

            content()
        }

        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
fun TopBarTitle(
    onBackClick: () -> Unit,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            ClickableIcon(
                painter = painterResource(R.drawable.back_arrow_icon),
                contentDescription = stringResource(R.string.back_button),
                onClick = onBackClick
            )
        }

        HeadingMediumText(
            text = title
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}