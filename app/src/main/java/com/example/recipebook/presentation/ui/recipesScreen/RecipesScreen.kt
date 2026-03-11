package com.example.recipebook.presentation.ui.recipesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeCardList
import com.example.recipebook.presentation.util.toUpdatedAgoText
import com.example.recipebook.presentation.viewModel.recipesScreen.RecipesViewModel

@Composable
@Suppress("FunctionName")
fun RecipesScreen(
    viewModel: RecipesViewModel = hiltViewModel(),
    onRecipeDetail: (recipeId: String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (headingText, contentContainer) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextLarge(
            text = stringResource(R.string.recipes),
            modifier = Modifier
                .constrainAs(headingText) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(contentContainer) {
                    linkTo(start = startGuideline, end = endGuideline,
                        top = headingText.bottom, bottom = parent.bottom,
                        topMargin = 24.dp)
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(
                items = uiState.newRecipes,
                key = { it.id }) { recipe ->
                RecipeCardList(
                    recipeId = recipe.id,
                    imageUrl = recipe.imageUrl,
                    categoryResource = when (recipe.category) {
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
                    onRecipeClick = { onRecipeDetail(recipe.id) },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}