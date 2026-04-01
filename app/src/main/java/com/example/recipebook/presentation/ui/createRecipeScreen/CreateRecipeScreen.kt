package com.example.recipebook.presentation.ui.createRecipeScreen

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
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.presentation.controller.LocalSnackBarController
import com.example.recipebook.presentation.ui.commonUi.IngredientDialog
import com.example.recipebook.presentation.ui.commonUi.IngredientTextBox
import com.example.recipebook.presentation.ui.commonUi.HeadingMediumText
import com.example.recipebook.presentation.ui.commonUi.IconTextButton
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.SingleActionText
import com.example.recipebook.presentation.ui.commonUi.BodyMediumText
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.CustomTextButton
import com.example.recipebook.presentation.ui.commonUi.CustomTimePicker
import com.example.recipebook.presentation.ui.commonUi.EditDescriptionBottomSheet
import com.example.recipebook.presentation.ui.commonUi.LimitedTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.recipe.RecipeStepBox
import com.example.recipebook.presentation.ui.createRecipeScreen.model.CategoryMenuItem
import com.example.recipebook.presentation.ui.createRecipeScreen.model.MeasureMenuItem
import com.example.recipebook.presentation.viewModel.createRecipeScreen.CreateRecipeViewModel
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.toUiSource
import com.example.recipebook.presentation.viewModel.createRecipeScreen.model.CreateRecipeEvent
import com.example.recipebook.presentation.viewModel.model.EditTarget

@Composable
@Suppress("FunctionName")
fun CreateRecipeScreen(
    onBack: () -> Unit,
    viewModel: CreateRecipeViewModel = hiltViewModel()
) {
    val resources = LocalResources.current
    val snackBarHostState = LocalSnackBarController.current
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

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CreateRecipeEvent.MinIngredientCountLimit -> {
                    snackBarHostState.showMessage(message = resources.getString(R.string.minIngredientCountMessage))
                }

                is CreateRecipeEvent.MaxIngredientCountLimit -> {
                    snackBarHostState.showMessage(message = resources.getString(R.string.maxIngredientCountMessage))

                }
            }
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (recipeColumn, headingText, closeButton, button) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .constrainAs(closeButton) {
                    centerVerticallyTo(headingText)
                    start.linkTo(startGuideline)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_icon),
                contentDescription = stringResource(R.string.cancel_icon)
            )
        }

        HeadingMediumText(
            text = stringResource(R.string.create_recipe),
            modifier = Modifier
                .constrainAs(headingText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        CustomTextButton(
            onClick = { viewModel.uploadNewRecipe(onBack) },
            text = stringResource(R.string.save_button),
            modifier = Modifier.constrainAs(button) {
                centerVerticallyTo(headingText)
                end.linkTo(endGuideline)
            }
        )

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
                    textFieldValue = uiState.recipeName.value,
                    onValueChange = viewModel::onRecipeNameChanged,
                    onClearText = { viewModel.onRecipeNameChanged("") },
                    textLengthLimit = 100,
                    textHint = stringResource(R.string.recipe_name_hint),
                    errorText = when (uiState.recipeName.error) {
                        is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                        is ValidationError.SymbolLimit -> stringResource(R.string.field_length_limit)
                        else -> null
                    },
                    modifier = Modifier.padding(top = 32.dp)
                )
            }

            item {
                SingleActionTextBox(
                    title = stringResource(R.string.recipe_description),
                    value = uiState.description.value,
                    hint = stringResource(R.string.recipe_description_hint),
                    errorText = when (uiState.description.error) {
                        is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                        is ValidationError.SymbolLimit -> stringResource(R.string.field_length_limit)
                        else -> null
                    },
                    contentDescription = stringResource(
                        (R.string.recipe_description)
                    ),
                    onClick = {
                        viewModel.setEditTargetObject(
                            editTargetObject = EditTarget.Description(uiState.description.value)
                        )
                    },
                    painter = null
                )
            }

            item {
                SingleActionTextBox(
                    title = stringResource(R.string.time_estimation),
                    value = uiState.timeEstimationUiState?.toDisplayString(
                        hourLabel = stringResource(R.string.time_estimation_hours),
                        minuteLabel = stringResource(R.string.time_estimation_minutes)
                    ).orEmpty(),
                    hint = stringResource(R.string.recipe_time_estimation_hint),
                    errorText = null,
                    contentDescription = "",
                    painter = null,
                    onClick = { viewModel.showTimePickerDialog(true) }
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
                            errorText = "",
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
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            item {
                SingleActionTextBox(
                    title = stringResource(R.string.category),
                    value = if (uiState.recipeCategory.value.isNotEmpty()) {
                        stringResource(
                            CategoryMenuItem.from(uiState.recipeCategory.value)
                                .stringResource
                        )
                    } else "",
                    hint = stringResource(R.string.category_hint),
                    errorText = null,
                    contentDescription = "",
                    onClick = { viewModel.showCategoryMenu(true) },
                    painter = null,
                    modifier = Modifier.padding(bottom = 12.dp)
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

                        RecipeStepBox(
                            index = index,
                            imageSource = recipeStep.imageSource,
                            titleValue = recipeStep.title.value,
                            titleLengthLimit = 100,
                            titleErrorText = when (recipeStep.title.error) {
                                is ValidationError.SymbolLimit -> stringResource(R.string.field_length_limit)

                                is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                                else -> null
                            },
                            descriptionValue = recipeStep.stepDescription.value,
                            descriptionErrorText = when (recipeStep.stepDescription.error) {
                                is ValidationError.SymbolLimit -> stringResource(
                                    R.string.field_length_limit
                                )

                                is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                                else -> null
                            },
                            onImageChange = debounce { imagePicker.launch("image/*") },
                            onTitleChange = { newValue ->
                                viewModel.onStepTitleChange(recipeStep.id, newValue)
                            },
                            onDescriptionChange = {
                                viewModel.setEditTargetObject(
                                    EditTarget.StepDescription(
                                        stepId = recipeStep.id,
                                        description = recipeStep.stepDescription.value
                                    )
                                )
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
                }
            }

            item {
                IconTextButton(
                    painter = painterResource(R.drawable.upload_recipe_icon),
                    text = stringResource(R.string.add_steps),
                    onClick = { viewModel.addStep() },
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }
        }


        val target = uiState.editTargetDescriptionObject
        val initialText = when (target) {
            is EditTarget.Description -> uiState.description.value
            is EditTarget.StepDescription -> uiState.recipeSteps.first { it.id == target.stepId }.stepDescription.value
            else -> ""
        }

        if (target != null) {
            EditDescriptionBottomSheet(
                initialText = initialText,
                textLimit = 1500,
                onDismiss = { viewModel.setEditTargetObject(editTargetObject = null) },
                onConfirm = viewModel::setDescription
            )
        }

        CustomTimePicker(
            isShow = uiState.isTimePickerDialogOpen,
            initialHour = uiState.timeEstimationUiState?.hour ?: 0,
            initialMinute = uiState.timeEstimationUiState?.minute ?: 0,
            onDismiss = { viewModel.showTimePickerDialog(false) },
            onConfirm = { hours, minute ->
                viewModel.onTimeEstimationChange(hours, minute)
            }
        )

        IngredientDialog(
            editingIngredient = uiState.editingIngredient,
            onDismiss = { viewModel.showIngredientDialog(null) },
            onConfirm = viewModel::onIngredientChange
        )
    }
}