package com.example.recipebook.presentation.ui.editRecipeScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.IngredientDialog
import com.example.recipebook.presentation.ui.commonUi.DoubleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.IconTextButton
import com.example.recipebook.presentation.ui.commonUi.RecipeStepBox
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.ui.commonUi.TitleTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.CustomTextButton
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.createRecipeScreen.model.CategoryMenuItem
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.toUiSource
import com.example.recipebook.presentation.viewModel.editRecipeScreen.EditRecipeViewModel
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeEvent

@Composable
@Suppress("FunctionName")
fun EditRecipeScreen(
    onBack: () -> Unit,
    viewModel: EditRecipeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is EditRecipeEvent.GoBack -> onBack()

            }
        }
    }
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val recipeImagePickerLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onRecipeImagePicked(uri)
        }
    }
    val categoryMenuItems = listOf(
        CategoryMenuItem.APPETIZER,
        CategoryMenuItem.SALAD,
        CategoryMenuItem.SOUP,
        CategoryMenuItem.MAIN,
        CategoryMenuItem.GARNISH,
        CategoryMenuItem.SAUCE,
        CategoryMenuItem.DESERT,
        CategoryMenuItem.DRINK
    )

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (recipeColumn, headingText, closeButton, button) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextMedium(
            text = stringResource(R.string.edit_recipe),
            modifier = Modifier
                .constrainAs(headingText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        IconButton(
            onClick = { viewModel.goBack() },
            modifier = Modifier
                .constrainAs(closeButton) {
                    centerVerticallyTo(headingText)
                    end.linkTo(endGuideline)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_icon),
                contentDescription = stringResource(R.string.cancel_icon)
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .constrainAs(recipeColumn) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(headingText.bottom, margin = 24.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }) {
            item {
                val imageModifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)

                val imageSource = uiState.recipeImageSource.toUiSource()

                if (imageSource == null) {
                    UploadImageBox(
                        text = stringResource(R.string.upload_photo),
                        modifier = imageModifier,
                        onClick = debounce { recipeImagePickerLaunch.launch("image/*") },
                        cornerShapeDp = 20.dp
                    )
                } else {
                    ImageCover(
                        imageSource = imageSource,
                        contentDescription = stringResource(R.string.recipe_image),
                        modifier = imageModifier,
                        onCancelClick = { viewModel.onRecipeImagePicked(null) }
                    )
                }
            }

            item {
                TitleTextFieldBox(
                    title = stringResource(R.string.recipe_name),
                    textFieldValue = uiState.recipeName,
                    onValueChange = viewModel::onRecipeNameChanged,
                    textHint = stringResource(R.string.recipe_name_hint),
                    isError = false,
                    modifier = Modifier.padding(top = 32.dp)
                )
            }

            item {
                TitleTextFieldBox(
                    title = stringResource(R.string.recipe_description),
                    textFieldValue = uiState.recipeDescription,
                    onValueChange = viewModel::onRecipeDescriptionChanged,
                    textHint = stringResource(R.string.recipe_description_hint),
                    isError = false
                )
            }

            item {
                TitleTextFieldBox(
                    title = stringResource(R.string.time_estimation),
                    textFieldValue = uiState.timeEstimation,
                    onValueChange = viewModel::onRecipeTimeEstimationChanged,
                    textHint = stringResource(R.string.recipe_time_estimation_hint),
                    isError = false
                )
            }

            item {
                TitleText(
                    text = stringResource(R.string.add_ingredients),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(
                items = uiState.ingredients,
                key = { it.id }
            ) { ingredient ->
                DoubleActionTextBox(
                    ingredient = ingredient.value,
                    amount = ingredient.amount,
                    measure = if (ingredient.measure.isNotEmpty()) {
                        stringResource(MeasureMenuItem.from(ingredient.measure).stringResource)
                    } else "",
                    hint = stringResource(R.string.add_ingredient),
                    onBoxClick = { viewModel.showIngredientDialog(ingredient.id) },
                    onIconClick = { viewModel.removeIngredient(ingredient.id) }
                )
            }

            item {
                IconTextButton(
                    painter = painterResource(R.drawable.upload_recipe_icon),
                    text = stringResource(R.string.add_ingredients),
                    onClick = { viewModel.addIngredient() },
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

            item {
                TitleText(
                    text = stringResource(R.string.category),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                SingleActionTextBox(
                    value = if (uiState.recipeCategory.isNotEmpty()) {
                        stringResource(
                            CategoryMenuItem.from(uiState.recipeCategory)
                                .stringResource
                        )
                    } else "",
                    hint = stringResource(R.string.category_hint),
                    isError = null,
                    contentDescription = "",
                    onClick = { viewModel.showCategoryMenu(true) },
                    painter = null,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                AppDropdownMenu(
                    expanded = uiState.isCategoryMenuExpand,
                    items = categoryMenuItems,
                    itemContent = { menuItem ->
                        Text(stringResource(menuItem.stringResource))
                    },
                    onItemClick = { menuItem ->
                        viewModel.onCategoryChange(menuItem.toString())
                    },
                    onDismiss = { viewModel.showCategoryMenu(false) },
                )
            }

            item {
                TitleText(
                    text = stringResource(R.string.step_by_step),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            items(
                items = uiState.recipeSteps,
                key = { it.id }
            ) { recipeStep ->
                val imagePicker = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    viewModel.onStepImageChange(recipeStep.id, uri)
                }
                val imageSource = recipeStep.imageSource.toUiSource()

                RecipeStepBox(
                    imageSource = imageSource,
                    titleValue = recipeStep.title,
                    descriptionValue = recipeStep.stepDescription,
                    onImageChange = debounce { imagePicker.launch("image/*") },
                    onTitleChange = { newValue ->
                        viewModel.onStepTitleChange(recipeStep.id, newValue)
                    },
                    onDescriptionChange = { newValue ->
                        viewModel.onStepDescriptionChange(recipeStep.id, newValue)
                    },
                    onDeleteClick = debounce { viewModel.removeStep(recipeStep.id) },
                    onCancelImageClick = debounce {
                        viewModel.onStepImageChange(
                            recipeStep.id,
                            null
                        )
                    }
                )
            }

            item {
                IconTextButton(
                    painter = painterResource(R.drawable.upload_recipe_icon),
                    text = stringResource(R.string.add_steps),
                    onClick = { viewModel.addStep() },
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

            item {
                SquareRoundedButton(
                    onClick = { viewModel.updateRecipe() },
                    text = stringResource(R.string.save_button),
                    isLoading = false,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }

        uiState.editingIngredientId?.let { ingredientId ->
            IngredientDialog(
                onDialogDismiss = { viewModel.showIngredientDialog(null) },
                onConfirm = { ingredientValue, amount, measure ->
                    viewModel.onIngredientChange(
                        id = ingredientId,
                        value = ingredientValue,
                        amount = amount,
                        measure = measure
                    )
                }
            )
        }
    }
}