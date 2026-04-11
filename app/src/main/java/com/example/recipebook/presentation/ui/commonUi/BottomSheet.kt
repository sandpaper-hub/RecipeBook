package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.collection.CollectionListCard
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.CollectionUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
fun CreateBottomSheet(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onCreateRecipeScreen: () -> Unit,
    onCreateCollectionScreen: () -> Unit
) {
    if (showSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = onDismiss
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                val (dismissButton, titleText, createContainer) = createRefs()


                HeadingMediumText(
                    text = stringResource(R.string.create_something),
                    modifier = Modifier
                        .constrainAs(titleText) {
                            centerHorizontallyTo(parent)
                            top.linkTo(parent.top)
                        },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .constrainAs(dismissButton) {
                            linkTo(top = titleText.top, bottom = titleText.bottom)
                            end.linkTo(parent.end, margin = 24.dp)
                        }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.delete_icon),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.inversePrimary
                    )
                }

                Row(
                    modifier = Modifier
                        .constrainAs(createContainer) {
                            centerHorizontallyTo(parent)
                            linkTo(top = titleText.bottom, bottom = parent.bottom)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TitleIconButton(
                        painterResource(R.drawable.cook_hat_icon),
                        title = stringResource(R.string.new_recipe),
                        onClick = onCreateRecipeScreen,
                        contentDescription = ""
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    TitleIconButton(
                        painterResource(R.drawable.collection_icon),
                        title = stringResource(R.string.new_collection),
                        onClick = onCreateCollectionScreen,
                        contentDescription = ""
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("FunctionName")
fun CollectionsBottomSheet(
    collections: List<CollectionUiState>,
    showSheet: Boolean,
    onDismiss: () -> Unit,
    toggleRecipeInCollection: (collectionId: String) -> Unit
) {
    if (showSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest = onDismiss
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HeadingMediumText(
                    text = stringResource(R.string.added_to_collection),
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                LazyColumn(
                    modifier = Modifier.padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(collections) { collectionUiState ->
                        CollectionListCard(
                            clickEnabled = !collectionUiState.isUpdating,
                            name = collectionUiState.name,
                            imageUrl = collectionUiState.imageUrl,
                            isRecipeContainCollection = collectionUiState.containRecipe,
                            onItemClick = {
                                toggleRecipeInCollection(collectionUiState.id)
                            })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDescriptionBottomSheet(
    initialText: String,
    textLimit: Int,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by rememberSaveable(initialText) { mutableStateOf(initialText) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    val textLimitErrorColor by animateColorAsState(
        if (text.length > textLimit) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.primary
        }
    )
    ModalBottomSheet(
        sheetState = sheetState,
        sheetGesturesEnabled = false,
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            val (dismissButton, titleText, okButton, textField,
                symbolLimitText, symbolCounterText) = createRefs()

            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .constrainAs(dismissButton) {
                        linkTo(top = titleText.top, bottom = titleText.bottom)
                        start.linkTo(parent.start, margin = 8.dp)
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete_icon),
                    contentDescription = stringResource(R.string.cancel_icon),
                    tint = MaterialTheme.colorScheme.inversePrimary
                )
            }

            HeadingMediumText(
                text = stringResource(R.string.edit_description),
                modifier = Modifier
                    .constrainAs(titleText) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                    },
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )

            CustomTextButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        onConfirm(text)
                    }
                },
                text = stringResource(R.string.ok_botton),
                modifier = Modifier.constrainAs(okButton) {
                    centerVerticallyTo(titleText)
                    end.linkTo(parent.end, margin = 8.dp)
                }
            )

            BodyMediumText(
                modifier = Modifier.constrainAs(symbolLimitText) {
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                },
                text = stringResource(R.string.symbols_limit, textLimit),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textLimitErrorColor
                )
            )

            BodyMediumText(
                modifier = Modifier.constrainAs(symbolCounterText) {
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                },
                text = "${text.length}/$textLimit",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = textLimitErrorColor
                )
            )

            BasicTextField(
                modifier = Modifier
                    .constrainAs(textField) {
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                            startMargin = 24.dp,
                            endMargin = 24.dp
                        )
                        linkTo(
                            top = titleText.bottom,
                            bottom = symbolCounterText.top,
                            topMargin = 24.dp,
                            bottomMargin = 24.dp
                        )
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                value = text,
                onValueChange = { text = it },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            )
        }
    }
}