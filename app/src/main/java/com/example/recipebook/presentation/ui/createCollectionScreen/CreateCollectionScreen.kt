package com.example.recipebook.presentation.ui.createCollectionScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.recipebook.presentation.ui.commonUi.CustomTextButton
import com.example.recipebook.presentation.ui.commonUi.EditDescriptionBottomSheet
import com.example.recipebook.presentation.ui.commonUi.HeadingMediumText
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.LimitedTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.viewModel.createCollectionScreen.CreateCollectionViewModel
import com.example.recipebook.presentation.viewModel.createCollectionScreen.model.CreateCollectionEvent

@Composable
@Suppress("FunctionName")
fun CreateCollectionScreen(
    onBack: () -> Unit,
    viewModel: CreateCollectionViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val collectionImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onImageChange(uri)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                CreateCollectionEvent.GoBack -> onBack()
            }
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (closeButton, headingText,
            collectionColumn, saveButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        IconButton(
            modifier = Modifier.constrainAs(closeButton) {
                centerVerticallyTo(headingText)
                start.linkTo(startGuideline)
            },
            onClick = { viewModel.onBack() }
        ) {
            Icon(
                painter = painterResource(R.drawable.delete_icon),
                contentDescription = stringResource(R.string.cancel_icon)
            )
        }

        CustomTextButton(
            onClick = { viewModel.createCollection() },
            text = stringResource(R.string.save_button),
            modifier = Modifier.constrainAs(saveButton) {
                centerVerticallyTo(headingText)
                end.linkTo(endGuideline)
            }
        )

        HeadingMediumText(
            text = stringResource(R.string.create_collection),
            modifier = Modifier.constrainAs(headingText) {
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, margin = 24.dp)
            }
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .constrainAs(collectionColumn) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(headingText.bottom, margin = 24.dp)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        ) {
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(150.dp)

            if (uiState.imageSource != null) {
                ImageCover(
                    imageSource = uiState.imageSource.toString(),
                    contentDescription = stringResource(R.string.collection_image),
                    onCancelClick = { viewModel.onImageChange(null) },
                    modifier = imageModifier
                )
            } else {
                UploadImageBox(
                    text = stringResource(R.string.upload_photo),
                    modifier = imageModifier,
                    onClick = debounce { collectionImagePickerLauncher.launch("image/*") },
                    cornerShapeDp = 20.dp
                )
            }

            LimitedTextFieldBox(
                title = stringResource(R.string.collection_name),
                textFieldValue = uiState.name,
                onValueChange = viewModel::onNameChange,
                onClearText = {viewModel.onNameChange("")},
                textLengthLimit = 100,
                textHint = stringResource(R.string.collection_hint),
                isError = false
            )


            SingleActionTextBox(
                title = stringResource(R.string.recipe_description),
                value = uiState.description.descriptionValue,
                hint = stringResource(R.string.collection_description_hint),
                isError = false,
                contentDescription = stringResource(R.string.collection_description),
                onClick = { viewModel.showDescriptionBottomSheet(uiState.description) },
                painter = null
            )
        }
    }

    if (uiState.editableObject != null) {
        EditDescriptionBottomSheet(
            onDismiss = { viewModel.showDescriptionBottomSheet(null) },
            editableObject = uiState.editableObject,
            onConfirm = viewModel::setDescription,
            onDescriptionChange = viewModel::onEditableObjectChange
        )
    }
}