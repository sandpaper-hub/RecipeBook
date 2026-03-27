package com.example.recipebook.presentation.ui.searchScreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
import com.example.recipebook.presentation.ui.commonUi.HeadingLargeText
import com.example.recipebook.presentation.ui.commonUi.SearchTextField
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeCardList
import com.example.recipebook.presentation.util.toUpdatedAgoText
import com.example.recipebook.presentation.viewModel.model.RecipeUiState
import com.example.recipebook.presentation.viewModel.searchScreen.SearchViewModel
import com.example.recipebook.presentation.viewModel.searchScreen.model.ContentState

@Composable
@Suppress("FunctionName")
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onRecipeDetail: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (headingText, searchTextField, contentContainer) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingLargeText(
            text = stringResource(R.string.search_text),
            modifier = Modifier
                .constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        SearchTextField(
            value = uiState.searchText,
            onValueChange = viewModel::onSearchTextChanged,
            onClearText = {viewModel.onSearchTextChanged("")},
            hint = stringResource(R.string.search_input_hint),
            modifier = Modifier
                .constrainAs(searchTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(headingText.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }
        )

        Box(
            modifier = Modifier
                .constrainAs(contentContainer) {
                    linkTo(
                        top = searchTextField.bottom, bottom = parent.bottom,
                        start = startGuideline, end = endGuideline,
                        topMargin = 12.dp
                    )
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentAlignment = Alignment.Center
        ) {
            when (uiState.contentState) {
                is ContentState.Empty -> {
                    PlaceholderMessage(
                        imageResource = R.drawable.empty_state,
                        textResource = R.string.empty_state_text
                    )
                }

                is ContentState.SearchContent -> {
                    SearchContent(
                        items = (uiState.contentState as ContentState.SearchContent).recipeList,
                        onItemClick = onRecipeDetail
                    )
                }

                is ContentState.Loading -> CircularProgressIndicator()
                is ContentState.NothingsFound -> PlaceholderMessage(
                    imageResource = R.drawable.no_search_result,
                    textResource = R.string.no_search_result
                )
                is ContentState.NoInternet -> PlaceholderMessage(
                    imageResource = R.drawable.no_internet,
                    textResource = R.string.no_internet
                )
                is ContentState.UnknownError -> PlaceholderMessage(
                    imageResource = R.drawable.unknown_error,
                    textResource = R.string.unknown_error
                )
            }
        }
    }
}

@Composable
private fun SearchContent(
    items: List<RecipeUiState>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items, key = { it.id }) { recipe ->
            RecipeCardList(
                recipeId = recipe.id,
                imageUrl = recipe.imageSource,
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
                name = recipe.name,
                timeEstimation = recipe.timeEstimationUiState.toDisplayString(
                    hourLabel = stringResource(R.string.time_estimation_hours),
                    minuteLabel = stringResource(R.string.time_estimation_minutes)
                ),
                uploadedTime = recipe.uploadedTime.toUpdatedAgoText(),
                onRecipeClick = onItemClick,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun PlaceholderMessage(@DrawableRes imageResource: Int, @StringRes textResource: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(imageResource),
            contentDescription = stringResource(textResource),
            tint = MaterialTheme.colorScheme.inversePrimary
        )

        Text(
            modifier = Modifier
                .width(300.dp),
            text = stringResource(textResource),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.inversePrimary
            )
        )
    }
}