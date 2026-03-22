package com.example.recipebook.presentation.ui.editCollectionScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.recipebook.presentation.ui.commonUi.HeadingMediumText
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.toUiSource
import com.example.recipebook.presentation.viewModel.collectionEditScreen.CollectionEditViewModel
import com.example.recipebook.presentation.viewModel.editRecipeScreen.model.EditRecipeEvent

@Composable
@Suppress("FunctionName")
fun CollectionEditScreen(
    onBack: () -> Unit,
    viewModel: CollectionEditViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is EditRecipeEvent.GoBack -> {
                    onBack()
                }
            }
        }
    }
    val collectionImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onImageChange(uri)
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (closeButton, headingText, imagePickerBox, collectionNameBox,
            collectionDescriptionBox, saveButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingMediumText(
            text = stringResource(R.string.create_collection),
            modifier = Modifier.constrainAs(headingText) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, margin = 24.dp)
            }
        )

        IconButton(
            modifier = Modifier.constrainAs(closeButton) {
                centerVerticallyTo(headingText)
                end.linkTo(endGuideline)
            },
            onClick = { viewModel.onBack() }
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_icon),
                contentDescription = stringResource(R.string.cancel_icon)
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(imagePickerBox) {
                    centerHorizontallyTo(parent)
                    top.linkTo(headingText.bottom, margin = 24.dp)
                }
                .padding(horizontal = 24.dp)
        ) {
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(150.dp)

            val imageSource = uiState.imageSource.toUiSource()

            if (imageSource == null) {
                UploadImageBox(
                    text = stringResource(R.string.upload_photo),
                    modifier = imageModifier,
                    onClick = debounce { collectionImagePickerLauncher.launch("image/*") },
                    cornerShapeDp = 20.dp
                )
            } else {
                ImageCover(
                    imageSource = imageSource,
                    contentDescription = stringResource(R.string.collection_image),
                    onCancelClick = { viewModel.onImageChange(null) },
                    modifier = imageModifier
                )
            }
        }

        TitleTextFieldBox(
            title = stringResource(R.string.collection_name),
            textFieldValue = uiState.name,
            onValueChange = viewModel::onNameChange,
            textHint = stringResource(R.string.collection_hint),
            isError = false,
            modifier = Modifier.constrainAs(collectionNameBox) {
                linkTo(start = startGuideline, end = endGuideline)
                top.linkTo(imagePickerBox.bottom, margin = 32.dp)
                width = Dimension.fillToConstraints
            }
        )

        TitleTextFieldBox(
            title = stringResource(R.string.recipe_description),
            textFieldValue = uiState.description,
            onValueChange = viewModel::onDescriptionChange,
            textHint = stringResource(R.string.collection_description_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(collectionDescriptionBox) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(collectionNameBox.bottom)
                    width = Dimension.fillToConstraints
                }
        )

        SquareRoundedButton(
            onClick = { viewModel.updateCollection() },
            text = stringResource(R.string.save_button),
            isLoading = false,
            modifier = Modifier
                .constrainAs(saveButton) {
                    linkTo(start = startGuideline, end = endGuideline)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }
        )
    }
}