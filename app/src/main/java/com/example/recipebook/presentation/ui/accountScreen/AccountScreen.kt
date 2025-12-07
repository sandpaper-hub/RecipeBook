package com.example.recipebook.presentation.ui.accountScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SelectableButton
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleText

@Composable
@Suppress("FunctionName")
fun AccountScreen() {
    val fullName by remember { mutableStateOf("") }
    val email by remember { mutableStateOf("") }
    val region by remember { mutableStateOf("") }
    val dateOfBirth by remember { mutableStateOf("") }
    val genderOptions = listOf("Male", "Female")
    var selectedOptions by remember { mutableStateOf("Male") }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (backButton, headingText, nameText, nameTextField, emailText, emailTextField,
            regionText, regionTextField, dateBirthText, dateBirthTextField,
            genderText, genderButtons, saveButton) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        ClickableIcon(
            painter = painterResource(R.drawable.back_arrow_icon),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .constrainAs(backButton) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 16.dp)
                },
            onClick = {}
        )

        HeadingTextMedium(
            text = stringResource(R.string.account_text),
            modifier = Modifier
                .constrainAs(headingText) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top, margin = 16.dp)
                }
        )

        TitleText(
            text = stringResource(R.string.full_name),
            modifier = Modifier
                .constrainAs(nameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(backButton.bottom, margin = 32.dp)
                }
        )

        CustomTextField(
            value = fullName,
            onValueChange = {},
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
            text = stringResource(R.string.email),
            modifier = Modifier
                .constrainAs(emailText) {
                    start.linkTo(startGuideline)
                    top.linkTo(nameTextField.bottom, margin = 28.dp)
                }
        )

        CustomTextField(
            value = email,
            onValueChange = {},
            hint = stringResource(R.string.email_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(emailTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(emailText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        TitleText(
            text = stringResource(R.string.region),
            modifier = Modifier
                .constrainAs(regionText) {
                    start.linkTo(startGuideline)
                    top.linkTo(emailTextField.bottom, margin = 28.dp)
                }
        )

        CustomTextField(
            value = region,
            onValueChange = {},
            hint = stringResource(R.string.region_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(regionTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(regionText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        TitleText(
            text = stringResource(R.string.date_of_birth),
            modifier = Modifier
                .constrainAs(dateBirthText) {
                    start.linkTo(startGuideline)
                    top.linkTo(regionTextField.bottom, margin = 28.dp)
                }
        )

        CustomTextField(
            value = dateOfBirth,
            onValueChange = {},
            hint = stringResource(R.string.date_of_birth_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(dateBirthTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(dateBirthText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        TitleText(
            text = stringResource(R.string.gender),
            modifier = Modifier
                .constrainAs(genderText) {
                    start.linkTo(startGuideline)
                    top.linkTo(dateBirthTextField.bottom, margin = 28.dp)
                }
        )

        Row(
            modifier = Modifier
                .constrainAs(genderButtons) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(genderText.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            SelectableButton(
                text = genderOptions[0],
                selected = selectedOptions == genderOptions[0],
                onClick = {selectedOptions = genderOptions[0]},
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(28.dp))

            SelectableButton(
                text = genderOptions[1],
                selected = selectedOptions == genderOptions[1],
                onClick = {selectedOptions = genderOptions[1]},
                modifier = Modifier.weight(1f)
            )
        }

        SquareRoundedButton(
            onClick = {},
            text = stringResource(R.string.save_change),
            isLoading = false,
            containerColor = null,
            modifier = Modifier.constrainAs(saveButton) {
                linkTo(start = startGuideline, end = endGuideline)
                top.linkTo(genderButtons.bottom, margin = 40.dp)
            }
        )
    }
}