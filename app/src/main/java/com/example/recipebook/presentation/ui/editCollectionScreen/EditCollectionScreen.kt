package com.example.recipebook.presentation.ui.editCollectionScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.presentation.ui.commonUi.EditDescriptionBottomSheet
import com.example.recipebook.presentation.ui.commonUi.HeadingMediumText
import com.example.recipebook.presentation.ui.commonUi.ImageCover
import com.example.recipebook.presentation.ui.commonUi.LimitedTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.toUiSource
import com.example.recipebook.presentation.viewModel.collectionEditScreen.CollectionEditViewModel
import com.example.recipebook.presentation.viewModel.collectionEditScreen.model.CollectionEditEvent
import com.example.recipebook.presentation.viewModel.model.EditTarget

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
                is CollectionEditEvent.GoBack -> {
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
        val (closeButton, headingText, collectionColumn) = createRefs()
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

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.constrainAs(collectionColumn) {
                linkTo(start = startGuideline, end = endGuideline)
                top.linkTo(headingText.bottom, margin = 24.dp)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            val imageSource = uiState.imageSource.toUiSource()
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(150.dp)

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

            LimitedTextFieldBox(
                title = stringResource(R.string.collection_name),
                textFieldValue = uiState.name.value,
                onValueChange = viewModel::onNameChange,
                onClearText = { viewModel.onNameChange("") },
                textLengthLimit = Constraints.MAX_COLLECTION_NAME_LENGTH,
                textHint = stringResource(R.string.collection_hint),
                errorText = when (uiState.name.error) {
                    is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                    is ValidationError.MaxSymbolLimit -> stringResource(R.string.field_length_limit)
                    else -> null
                }
            )

            SingleActionTextBox(
                title = stringResource(R.string.recipe_description),
                value = uiState.description.value,
                hint = stringResource(R.string.collection_description_hint),
                errorText = when (uiState.description.error) {
                    is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                    is ValidationError.MaxSymbolLimit -> stringResource(
                        R.string.max_symbols_limit,
                        Constraints.MAX_DESCRIPTION_LENGTH
                    )

                    else -> null
                },
                contentDescription = stringResource(R.string.collection_description),
                onClick = { viewModel.showDescriptionBottomSheet(EditTarget.Description(uiState.description.value)) },
                painter = null
            )

            Spacer(modifier = Modifier.weight(1f))

            SquareRoundedButton(
                onClick = { viewModel.updateCollection() },
                text = stringResource(R.string.save_button),
                isLoading = false,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        val target = uiState.editTargetObject
        val initialText = when (target) {
            is EditTarget.Description -> target.descriptionValue
            else -> ""
        }
        if (target != null) {
            EditDescriptionBottomSheet(
                initialText = initialText,
                textLimit = Constraints.MAX_DESCRIPTION_LENGTH,
                onDismiss = { viewModel.showDescriptionBottomSheet(null) },
                onConfirm = viewModel::setDescription,
            )
        }
    }
}