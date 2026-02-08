package com.example.recipebook.presentation.ui.cooking_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.dropDownMenu.CustomDropDownMenu
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeStep
import com.example.recipebook.presentation.ui.commonUi.recipe.StepsIndicator
import com.example.recipebook.presentation.viewModel.cookingScreen.CookingViewModel

@Composable
@Suppress("FunctionName")
fun CookingScreen(
    viewModel: CookingViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState = viewModel.uiState
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { uiState.recipeSteps.size }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(58.dp)
        ) {
            Spacer(modifier = Modifier.width(24.dp))

            ClickableIcon(
                painter = painterResource((R.drawable.back_arrow_icon)),
                contentDescription = stringResource(R.string.back_button),
                onClick = onBack
            )

            Spacer(modifier = Modifier.weight(1f))

            ClickableIcon(
                painter = painterResource(R.drawable.list_pages_icon),
                contentDescription = stringResource(R.string.list_pages),
                onClick = {}
            )

            CustomDropDownMenu(
                menuItems = listOf("ABC"),
                isExpanded = false,
                onDismissRequest = {},
                onItemClick = { item -> },
                modifier = Modifier
            )

            Spacer(modifier = Modifier.width(24.dp))
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
            modifier = Modifier.weight(1f)
        ) { index ->
            RecipeStep(
                imageUrl = uiState.recipeSteps[index].imageUrl,
                title = uiState.recipeSteps[index].title,
                description = uiState.recipeSteps[index].description
            )
        }

        StepsIndicator(
            pagesCount = uiState.recipeSteps.size,
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}