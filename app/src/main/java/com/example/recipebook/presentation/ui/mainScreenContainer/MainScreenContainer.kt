package com.example.recipebook.presentation.ui.mainScreenContainer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.recipebook.navigation.mainHomeGraph.MainHomeGraph
import com.example.recipebook.presentation.ui.commonUi.MainBottomNavigationBar

@Composable
@Suppress("FunctionName")
fun MainScreenContainer() {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { MainBottomNavigationBar(navController = bottomNavController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        MainHomeGraph(
            navController = bottomNavController,
            Modifier.padding(innerPadding)
        )
    }
}