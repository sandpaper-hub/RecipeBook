package com.example.recipebook.presentation.ui.accountScreen

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
import com.example.recipebook.presentation.ui.commonUi.CustomDropDownMenu
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.DatePickerDialog
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.HeadingTextMedium
import com.example.recipebook.presentation.ui.commonUi.SelectableButtonBox
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.viewModel.accountScreen.AccountViewModel
import com.example.recipebook.presentation.util.toFormatedDate

@Composable
@Suppress("FunctionName")
fun AccountScreen(
    onBackNavigation: () -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val genderOptions = listOf("Male", "Female")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (backButton, headingText, nameText, nameTextField,
            regionText, regionTextField, dateBirthText, dateBirthTextField,
            genderText, genderButtons, saveButton, datePicker, countryMenu) = createRefs()
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
            onClick = onBackNavigation
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
            text = stringResource(R.string.region),
            modifier = Modifier
                .constrainAs(regionText) {
                    start.linkTo(startGuideline)
                    top.linkTo(nameTextField.bottom, margin = 28.dp)
                }
        )

        SingleActionTextBox(
            value = uiState.region,
            hint = stringResource(R.string.region_hint),
            isError = null,
            contentDescription = stringResource(R.string.region),
            onClick = { viewModel.showCountryMenu(true) },
            painter = null,
            modifier = Modifier
                .constrainAs(regionTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(regionText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        CustomDropDownMenu(
            uiState.regionLocales,
            isExpanded = uiState.showRegionMenu,
            onDismissRequest = { viewModel.showCountryMenu(false) },
            onItemClick = viewModel::onCountryChange,
            modifier = Modifier
                .constrainAs(countryMenu) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(regionTextField.bottom)
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

        SingleActionTextBox(
            value = uiState.dateOfBirth?.toFormatedDate() ?: "",
            hint = stringResource(R.string.date_of_birth_hint),
            isError = null,
            contentDescription = stringResource(R.string.date_of_birth),
            onClick = { viewModel.showDatePicker(true) },
            painter = painterResource(R.drawable.date_icon),
            modifier = Modifier
                .constrainAs(dateBirthTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(dateBirthText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )
        DatePickerDialog(
            isOpen = uiState.showDatePicker,
            onConfirm = viewModel::selectConfirmedDate,
            onCancel = { viewModel.showDatePicker(false) },
            modifier = Modifier.constrainAs(datePicker) {
                centerHorizontallyTo(parent)
                centerVerticallyTo(parent)
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

        SelectableButtonBox(
            values = genderOptions,
            selectedValue = uiState.gender,
            onValueSelected = viewModel::onGenderChanged,
            modifier = Modifier
                .constrainAs(genderButtons) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(genderText.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                }
        )

        SquareRoundedButton(
            onClick = { viewModel.onSaveClick(onBackNavigation) },
            text = stringResource(R.string.save_change),
            isLoading = uiState.isSaving,
            containerColor = null,
            modifier = Modifier.constrainAs(saveButton) {
                linkTo(start = startGuideline, end = endGuideline)
                top.linkTo(genderButtons.bottom, margin = 40.dp)
            })
    }
}