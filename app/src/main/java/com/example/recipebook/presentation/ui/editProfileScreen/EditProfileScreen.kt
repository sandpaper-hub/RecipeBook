package com.example.recipebook.presentation.ui.editProfileScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.CustomCircleIconButton
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.ProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.RoundedPrimaryButton
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.viewModel.editProfileScreen.EditProfileViewModel
import com.example.recipebook.presentation.util.debounce

@Composable
@Suppress("FunctionName")
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onBackNavigation: () -> Unit
) {

    val uiState = viewModel.uiState

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onImagePicked(uri)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backButton, screenTitle, saveButton, profileImage,
            editProfileImageButton, nameText, nameTextField,
            usernameText, usernameTextField) = createRefs()

        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        ClickableIcon(
            painter = painterResource(R.drawable.back_arrow_icon),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .constrainAs(backButton) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 21.dp)
                },
            onClick =
                debounce {
                    viewModel.refreshImageUri()
                    onBackNavigation()
                }
        )

        HeadingTextMedium(
            text = stringResource(R.string.edit_profile),
            modifier = Modifier
                .constrainAs(screenTitle) {
                    linkTo(start = backButton.end, end = saveButton.start)
                    centerVerticallyTo(backButton)
                }
        )

        RoundedPrimaryButton(
            onClick = {
                viewModel.updateUserData(onBackNavigation)//TODO сразу сохранять + debounce
            },
            text = stringResource(R.string.save_button),
            isLoading = uiState.isSaving,
            modifier = Modifier
                .constrainAs(saveButton) {
                    end.linkTo(endGuideline)
                    centerVerticallyTo(backButton)
                }
        )


        ProfileAvatar(
            imageUrl = when {
                uiState.localImageSource != null -> {
                    uiState.localImageSource
                }

                else -> {
                    uiState.profileImageSource
                }
            },
            contentDescription = stringResource(R.string.profile_image),
            size = 120.dp,
            modifier = Modifier
                .constrainAs(profileImage) {
                    centerHorizontallyTo(parent)
                    top.linkTo(screenTitle.bottom, margin = 41.dp)
                }
        )

        CustomCircleIconButton(
            size = 35.dp,
            painter = painterResource(R.drawable.edit_icon),
            contentDescription = stringResource(R.string.edit_profile),
            onClick = debounce { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.constrainAs(editProfileImageButton) {
                bottom.linkTo(profileImage.bottom)
                end.linkTo(profileImage.end)
            }
        )

        TitleText(
            text = stringResource(R.string.full_name),
            modifier = Modifier
                .constrainAs(nameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(profileImage.bottom, margin = 32.dp)
                }
        )

        CustomTextField(
            value = uiState.fullName,
            onValueChange = viewModel::onNameChanged,
            hint = stringResource(R.string.name_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(nameTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(nameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        TitleText(
            text = stringResource(R.string.nick_name),
            modifier = Modifier
                .constrainAs(usernameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(nameTextField.bottom, margin = 24.dp)
                }
        )

        CustomTextField(
            value = uiState.nickName,
            onValueChange = viewModel::onNickNameChanged,
            hint = stringResource(R.string.email_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(usernameTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(usernameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )
    }
}