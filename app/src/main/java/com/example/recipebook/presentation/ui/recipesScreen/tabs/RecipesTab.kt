package com.example.recipebook.presentation.ui.recipesScreen.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeCardList
import com.example.recipebook.presentation.util.toUpdatedAgoText
import com.example.recipebook.presentation.viewModel.recipesScreen.RecipesViewModel

@Composable
@Suppress("FunctionName")
fun RecipesTab(
    listState: LazyListState,
    onRecipeDetail: (String) -> Unit,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(
            items = uiState.recipes,
            key = { it.id }) { recipe ->
            RecipeCardList(
                recipeId = recipe.id,
                imageUrl = recipe.imageUrl,
                category = recipe.category,
                name = recipe.recipeName,
                timeEstimation = recipe.recipeTimeEstimation,
                uploadedTime = recipe.createdAt.toUpdatedAgoText(),
                onRecipeClick = { onRecipeDetail(recipe.id) }
            )
        }
    }
}