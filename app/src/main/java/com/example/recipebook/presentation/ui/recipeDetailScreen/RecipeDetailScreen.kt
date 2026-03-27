package com.example.recipebook.presentation.ui.recipeDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.recipe.getRecipe.IngredientMeasure
import com.example.recipebook.domain.model.recipe.getRecipe.RecipeCategory
import com.example.recipebook.presentation.ui.commonUi.CollectionsBottomSheet
import com.example.recipebook.presentation.ui.commonUi.DeleteDialog
import com.example.recipebook.presentation.ui.commonUi.ImageBanner
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleLargeText
import com.example.recipebook.presentation.ui.commonUi.TopBarBackNavigation
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.recipe.IngredientTextBox
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeDescription
import com.example.recipebook.presentation.ui.recipeDetailScreen.model.RecipeDetailMenuItem
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.RecipeDetailViewModel
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.RecipeDetailEvent

@Composable
@Suppress("FunctionName")
fun RecipeDetailScreen(
    onBack: () -> Unit,
    onCookingScreen: (String) -> Unit,
    onRecipeEditScreen: (String) -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    val dropdownMenuResourceItems = listOf(
        RecipeDetailMenuItem.EDIT,
        RecipeDetailMenuItem.DELETE
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is RecipeDetailEvent.GoBack -> onBack()
                is RecipeDetailEvent.OnCookingScreen -> onCookingScreen(event.recipeId)
                is RecipeDetailEvent.OnRecipeEditScreen -> onRecipeEditScreen(event.recipeId)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBarBackNavigation(
            onBackClick = { viewModel.goBack() },
            onMenuClick = { viewModel.showDropdownMenu(true) }
        ) {
            AppDropdownMenu(
                expanded = uiState.isOpenDropdownMenu,
                items = dropdownMenuResourceItems,
                itemContent = { menuItem ->
                    Text(stringResource(menuItem.stringResource))
                },
                onDismiss = { viewModel.showDropdownMenu(false) },
                onItemClick = { menuItem ->
                    when (menuItem) {
                        RecipeDetailMenuItem.EDIT -> {
                            viewModel.onRecipeEdit()
                        }
                        RecipeDetailMenuItem.DELETE -> {
                            viewModel.openDeleteDialog(true)
                        }
                    }
                }
            )
        }

        ConstraintLayout(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            val (recipeImage, recipeNameText, ingredientBox, descriptionText, ingredientsText,
                addToCollectionButton) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            ImageBanner(
                imageUrl = uiState.imageUrl,
                contentDescription = stringResource(R.string.recipe_image),
                modifier = Modifier
                    .height(250.dp)
                    .constrainAs(recipeImage) {
                        linkTo(start = parent.start, end = parent.end)
                        top.linkTo(parent.top)
                    }
            )

            TitleLargeText(
                text = uiState.name,
                modifier = Modifier
                    .constrainAs(recipeNameText) {
                        linkTo(start = startGuideline, end = addToCollectionButton.start)
                        top.linkTo(recipeImage.bottom, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            IconButton(
                onClick = { viewModel.showCollectionSheet(true) },
                modifier = Modifier
                    .constrainAs(addToCollectionButton) {
                        linkTo(start = recipeNameText.end, end = endGuideline)
                        centerVerticallyTo(recipeNameText)
                    }) {
                Icon(
                    painter = painterResource(R.drawable.add_circle_icon),
                    contentDescription = ""
                )
            }

            RecipeDescription(
                timeEstimation = uiState.timeEstimationUiState.toDisplayString(
                    hourLabel = stringResource(R.string.time_estimation_hours),
                    minuteLabel = stringResource(R.string.time_estimation_minutes)
                ),
                descriptionText = uiState.description,
                categoryResource = when (uiState.category) {
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
                modifier = Modifier.constrainAs(descriptionText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(recipeNameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                })

            Text(
                text = "${stringResource(R.string.ingredients)} (${uiState.ingredients.size})",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .constrainAs(ingredientsText) {
                        start.linkTo(startGuideline)
                        top.linkTo(descriptionText.bottom, margin = 16.dp)
                    }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.constrainAs(ingredientBox) {
                    linkTo(start = startGuideline, end = endGuideline)
                    linkTo(
                        top = ingredientsText.bottom,
                        bottom = parent.bottom,
                        topMargin = 24.dp,
                        bottomMargin = 24.dp,
                        bias = 0F
                    )
                    width = Dimension.fillToConstraints
                }
            ) {
                uiState.ingredients.forEach { ingredient ->
                    IngredientTextBox(
                        value = ingredient.value,
                        measure = when (ingredient.measure) {
                            IngredientMeasure.TEASPOON -> stringResource(R.string.measure_teaspoon)
                            IngredientMeasure.TABLESPOON -> stringResource(R.string.measure_tablespoon)
                            IngredientMeasure.GRAM -> stringResource(R.string.measure_g)
                            IngredientMeasure.KILOGRAM -> stringResource(R.string.measure_kg)
                            IngredientMeasure.MILLILITER -> stringResource(R.string.measure_ml)
                            IngredientMeasure.LITER -> stringResource(R.string.measure_l)
                            IngredientMeasure.PCS -> stringResource(R.string.measure_pcs)
                            else -> stringResource(R.string.unknown_measure)
                        },
                        amount = ingredient.amount
                    )
                }
            }
        }

        if (uiState.isOpenedDeleteDialog) {
            DeleteDialog(
                headingText = stringResource(R.string.delete_recipe_title),
                warningText = stringResource(R.string.delete_recipe_warning_title),
                itemName = uiState.name,
                onDismiss = { viewModel.openDeleteDialog(false) },
                onConfirm = {
                    viewModel.deleteRecipe()
                    viewModel.openDeleteDialog(false)
                }
            )
        }

        SquareRoundedButton(
            onClick = {
                viewModel.onCookingScreen()
            },
            text = stringResource(R.string.lets_cook),
            isLoading = false,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        )


        CollectionsBottomSheet(
            collections = uiState.collectionsUiState,
            showSheet = uiState.isShowCollectionSheet,
            onDismiss = { viewModel.showCollectionSheet(false) },
            toggleRecipeInCollection = { collectionId ->
                viewModel.toggleRecipeInCollection(
                    collectionId
                )
            },
        )
    }
}