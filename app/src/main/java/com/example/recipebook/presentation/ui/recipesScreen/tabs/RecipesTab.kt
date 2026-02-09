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
import com.example.recipebook.R
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
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
            items = uiState.newRecipes,
            key = { it.id }) { recipe ->
            RecipeCardList(
                recipeId = recipe.id,
                imageUrl = recipe.imageUrl,
                categoryResource = when(recipe.category) {
                    RecipeCategory.APPETIZER -> R.string.appetizer
                    RecipeCategory.SALAD -> R.string.salad
                    RecipeCategory.SOUP -> R.string.soup
                    RecipeCategory.MAIN -> R.string.main
                    RecipeCategory.GARNISH -> R.string.garnish
                    RecipeCategory.SAUCE -> R.string.sauce
                    RecipeCategory.DESERT -> R.string.desert
                    RecipeCategory.DRINK -> R.string.drink
                    else -> R.string.unknown_measure
                },
                name = recipe.recipeName,
                timeEstimation = recipe.recipeTimeEstimation,
                uploadedTime = recipe.createdAt.toUpdatedAgoText(),
                onRecipeClick = { onRecipeDetail(recipe.id) }
            )
        }
    }
}