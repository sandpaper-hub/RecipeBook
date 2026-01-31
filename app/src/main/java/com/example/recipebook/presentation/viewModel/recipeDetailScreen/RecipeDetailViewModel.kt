package com.example.recipebook.presentation.viewModel.recipeDetailScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.recipebook.navigation.mainHomeGraph.recipesGraph.RecipesRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel(){
    init {
        Log.d("UISTATE", checkNotNull(savedStateHandle[RecipesRoutes.RecipeDetail.RECIPE_ID_ARG]))
    }
}