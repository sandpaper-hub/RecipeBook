package com.example.recipebook

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.navigation.rootNavGraph.RootNavGraph
import com.example.recipebook.presentation.viewModel.themeScreen.ThemeViewModel
import com.example.recipebook.theme.RecipeBookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val currentTheme by themeViewModel.theme.collectAsState()
            RecipeBookTheme(currentTheme) {
                RootNavGraph()
            }
        }
    }
}