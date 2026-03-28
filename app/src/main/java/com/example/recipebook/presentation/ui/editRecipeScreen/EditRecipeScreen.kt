package com.example.recipebook.presentation.ui.editRecipeScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.recipebook.presentation.ui.commonUi.IngredientTextBox
import com.example.recipebook.presentation.ui.commonUi.HeadingMediumText
import com.example.recipebook.presentation.ui.commonUi.IconTextButton
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.SingleActionText
import com.example.recipebook.presentation.ui.commonUi.BodyMediumText
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.CustomTimePicker
import com.example.recipebook.presentation.ui.commonUi.EditDescriptionBottomSheet
import com.example.recipebook.presentation.ui.commonUi.IngredientDialog
import com.example.recipebook.presentation.ui.commonUi.LimitedTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeStepBox
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
        val (recipeColumn, headingText, closeButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingMediumText(
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
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
                LimitedTextFieldBox(
                    title = stringResource(R.string.recipe_name),
                    textFieldValue = uiState.recipeName,
                    onValueChange = viewModel::onRecipeNameChanged,
                    onClearText = { viewModel.onRecipeNameChanged("") },
                    textLengthLimit = 100,
                    textHint = stringResource(R.string.recipe_name_hint),
                    isError = false
                )
            }

            item {
                SingleActionTextBox(
                    title = stringResource(R.string.recipe_description),
                    value = uiState.recipeDescription.descriptionValue,
                    hint = stringResource(R.string.recipe_description_hint),
                    isError = false,
                    contentDescription = stringResource(R.string.recipe_description),
                    onClick = { viewModel.showDescriptionBottomSheet(uiState.recipeDescription) },
                    painter = null
                )
            }

            item {
                SingleActionTextBox(
                    title = stringResource(R.string.time_estimation),
                    value = uiState.timeEstimationUiState.toDisplayString(
                        hourLabel = stringResource(R.string.time_estimation_hours),
                        minuteLabel = stringResource(R.string.time_estimation_minutes)
                    ),
                    hint = stringResource(R.string.recipe_time_estimation_hint),
                    isError = false,
                    contentDescription = stringResource(R.string.recipe_time_estimation_hint),
                    onClick = { viewModel.showTimeEstimationDialog(true) },
                    painter = null
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    BodyMediumText(
                        text = stringResource(R.string.add_ingredients),
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    uiState.ingredients.forEachIndexed { index, ingredient ->
                        IngredientTextBox(
                            index = index + 1,
                            ingredient = ingredient.value,
                            amount = ingredient.amount,
                            measure = if (ingredient.measure != MeasureMenuItem.NULL) {
                                stringResource(ingredient.measure.stringResource)
                            } else "",
                            hint = stringResource(R.string.add_ingredient),
                            onBoxClick = { viewModel.showIngredientDialog(ingredient) },
                            onIconClick = { viewModel.removeIngredient(ingredient.id) }
                        )
                    }
                }
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
                BodyMediumText(
                    text = stringResource(R.string.category),
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }

            item {
                SingleActionText(
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    BodyMediumText(
                        text = stringResource(R.string.step_by_step),
                        modifier = Modifier.padding(bottom = 12.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    uiState.recipeSteps.forEachIndexed { index, recipeStep ->
                        val imagePicker = rememberLauncherForActivityResult(
                            contract = ActivityResultContracts.GetContent()
                        ) { uri: Uri? ->
                            viewModel.onStepImageChange(recipeStep.id, uri)
                        }
                        val imageSource = recipeStep.imageSource.toUiSource()

                        RecipeStepBox(
                            index = index,
                            imageSource = imageSource,
                            titleValue = recipeStep.title,
                            descriptionValue = recipeStep.stepDescription.description,
                            onImageChange = debounce { imagePicker.launch("image/*") },
                            onTitleChange = { newValue ->
                                viewModel.onStepTitleChange(recipeStep.id, newValue)
                            },
                            onDescriptionChange = { viewModel.showDescriptionBottomSheet(recipeStep.stepDescription) },
                            onDeleteClick = debounce { viewModel.removeStep(recipeStep.id) },
                            onCancelImageClick = debounce {
                                viewModel.onStepImageChange(
                                    recipeStep.id,
                                    null
                                )
                            }
                        )
                    }
                }
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

        EditDescriptionBottomSheet(
            onDismiss = { viewModel.onEditableObjectChange(null) },
            editableObject = uiState.editableObject,
            onConfirm = viewModel::setDescription,
            onDescriptionChange = viewModel::onEditableObjectChange
        )


        CustomTimePicker(
            isShow = uiState.isTimeEstimationDialogOpen,
            initialHour = uiState.timeEstimationUiState.hour,
            initialMinute = uiState.timeEstimationUiState.minute,
            onDismiss = { viewModel.showTimeEstimationDialog(false) },
            onConfirm = { hour, minute ->
                viewModel.onTimeEstimationChanged(hour, minute)
            }
        )

        IngredientDialog(
            editingIngredient = uiState.editingIngredient,
            isMeasureMenuOpen = uiState.isMeasureMenuOpen,
            showMeasureMenu = viewModel::showMeasureMenu,
            onEditingIngredientChange = viewModel::onEditingIngredientChange,
            onDialogDismiss = { viewModel.showIngredientDialog(null) },
            onConfirm = viewModel::onIngredientChange

        )
    }
}