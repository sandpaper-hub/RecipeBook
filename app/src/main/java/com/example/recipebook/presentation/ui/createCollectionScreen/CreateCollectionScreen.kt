package com.example.recipebook.presentation.ui.createCollectionScreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.UploadImageBox

@Composable
@Suppress("FunctionName")
fun CreateCollectionScreen(
    onBack: () -> Unit
) {

    val collectionImagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {

        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (closeButton, headingText, imagePickerBox, collectionNameBox,
            collectionDescriptionBox, saveButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextMedium(
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
            onClick = onBack
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
            UploadImageBox(
                text = stringResource(R.string.upload_photo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                onClick = {},
                cornerShapeDp = 20.dp
            )
        }

        TitleTextFieldBox(
            title = stringResource(R.string.collection_name),
            textFieldValue = "",
            onValueChange = {},
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
            textFieldValue = "",
            onValueChange = {},
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
            onClick = {},
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