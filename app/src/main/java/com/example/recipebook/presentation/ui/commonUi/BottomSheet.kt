package com.example.recipebook.presentation.ui.commonUi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.collection.CollectionListCard
import com.example.recipebook.presentation.viewModel.recipeDetailScreen.model.CollectionUiState

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


                HeadingTextMedium(
                    text = "Create something",
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
                HeadingTextMedium(
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