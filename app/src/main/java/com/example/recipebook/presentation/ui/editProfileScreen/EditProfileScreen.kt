package com.example.recipebook.presentation.ui.editProfileScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.CustomIconButton
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.ProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.RoundedPrimaryButton
import com.example.recipebook.presentation.ui.commonUi.TitleText

@Composable
@Suppress("FunctionName")
fun EditProfileScreen(onBackNavigation: () -> Unit) {

    val fullName = ""
    val userName = ""

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backButton, screenTitle, saveButton, profileImage,
            editProfileImageButton, nameText, nameTextField,
            usernameText, usernameTextField) = createRefs()

        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        Icon(
            painter = painterResource(R.drawable.back_arrow_icon),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .constrainAs(backButton) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 21.dp)
                }
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        onBackNavigation()
                })
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
            onClick = { onBackNavigation() },
            text = stringResource(R.string.save_button),
            modifier = Modifier
                .constrainAs(saveButton) {
                    end.linkTo(endGuideline)
                    centerVerticallyTo(backButton)
                }
        )

        ProfileAvatar(
            imageUrl = null,
            contentDescription = stringResource(R.string.profile_image),
            size = 120.dp,
            modifier = Modifier
                .constrainAs(profileImage) {
                    centerHorizontallyTo(parent)
                    top.linkTo(screenTitle.bottom, margin = 41.dp)
                }
        )

        CustomIconButton(
            size = 35.dp,
            painter = painterResource(R.drawable.edit_icon),
            contentDescription = stringResource(R.string.edit_profile),
            onClick = {},
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
            value = fullName,
            onValueChange = {},
            hint = stringResource(R.string.name_hint),
            modifier = Modifier
                .constrainAs(nameTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(nameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        TitleText(
            text = stringResource(R.string.email),
            modifier = Modifier
                .constrainAs(usernameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(nameTextField.bottom, margin = 24.dp)
                }
        )

        CustomTextField(
            value = userName,
            onValueChange = {},
            hint = stringResource(R.string.email_hint),
            modifier = Modifier
                .constrainAs(usernameTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(usernameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )
    }
}