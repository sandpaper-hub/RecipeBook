package com.example.recipebook.presentation.ui.createRecipeScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.CustomDropDownMenu
import com.example.recipebook.presentation.ui.commonUi.IngredientDialog
import com.example.recipebook.presentation.ui.commonUi.DoubleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.IconTextButton
import com.example.recipebook.presentation.ui.commonUi.RecipeStepBox
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.ui.commonUi.TitleTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.viewModel.createRecipeScreen.CreateRecipeViewModel
import com.example.recipebook.presentation.util.debounce

@Composable
@Suppress("FunctionName")
fun CreateRecipeScreen(
    onBack: () -> Unit,
    viewModel: CreateRecipeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val listState = rememberLazyListState()
    val recipeImagePickerLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onRecipeImagePicked(uri)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (recipeColumn, headingText, closeButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextMedium(
            text = stringResource(R.string.create_recipe),
            modifier = Modifier
                .constrainAs(headingText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                }
        )

        IconButton(
            onClick = onBack,
            modifier = Modifier
                .constrainAs(closeButton) {
                    centerVerticallyTo(headingText)
                    end.linkTo(parent.end, margin = 24.dp)
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

                if (uiState.recipeImageUri != null) {
                    ImageCover(
                        imageUri = uiState.recipeImageUri,
                        contentDescription = stringResource(R.string.recipe_image),
                        modifier = imageModifier,
                        onCancelClick = { viewModel.onRecipeImagePicked(null) }
                    )
                } else {
                    UploadImageBox(
                        text = stringResource(R.string.upload_photo),
                        modifier = imageModifier,
                        onClick = debounce { recipeImagePickerLaunch.launch("image/*") },
                        cornerShapeDp = 20.dp
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
                    value = ingredient.ingredientValue,
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
                    value = uiState.recipeCategory,
                    hint = stringResource(R.string.category_hint),
                    isError = null,
                    contentDescription = "",
                    onClick = { viewModel.showCategoryMenu(true) },
                    painter = null,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                val categoryList = listOf(
                    "Main", "Desert", "Drink"
                )
                CustomDropDownMenu(
                    menuItems = categoryList,
                    isExpanded = uiState.isCategoryMenuExpand,
                    onDismissRequest = { viewModel.showCategoryMenu(false) },
                    onItemClick = viewModel::onCategoryChange,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
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

                RecipeStepBox(
                    imageUri = recipeStep.imageUri,
                    descriptionValue = recipeStep.stepDescription,
                    onImageChange = debounce { imagePicker.launch("image/*") },
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
                    onClick = { viewModel.uploadNewRecipe() },
                    text = stringResource(R.string.upload),
                    isLoading = false,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        }

        uiState.editingIngredientId?.let { ingredientId ->
            IngredientDialog(
                onDialogDismiss = { viewModel.showIngredientDialog(null) },
                onConfirm = { ingredientValue ->
                    viewModel.onIngredientChange(ingredientId, ingredientValue)
                }
            )
        }
    }
}