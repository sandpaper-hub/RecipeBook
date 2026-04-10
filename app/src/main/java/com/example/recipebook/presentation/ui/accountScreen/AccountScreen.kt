package com.example.recipebook.presentation.ui.accountScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.ClickableIcon
import com.example.recipebook.presentation.ui.commonUi.CustomCircleIconButton
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.DatePickerDialog
import com.example.recipebook.presentation.ui.commonUi.SingleActionText
import com.example.recipebook.presentation.ui.commonUi.HeadingMediumText
import com.example.recipebook.presentation.ui.commonUi.ProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.SelectableButtonBox
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.BodyMediumText
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.viewModel.accountScreen.AccountViewModel
import com.example.recipebook.presentation.util.toFormatedDate
import com.example.recipebook.presentation.util.toUiSource
import com.example.recipebook.presentation.viewModel.accountScreen.model.AccountUiEvent

@Composable
@Suppress("FunctionName")
fun AccountScreen(
    onBackNavigation: () -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val genderOptions = listOf("Male", "Female")

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.onImagePicked(uri)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                AccountUiEvent.GoBack -> onBackNavigation()
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topPanel, profileImage, editProfileImageButton, nameText, nameTextField,
            userNameText, userNameTextField, regionText, regionTextField, dateBirthText, dateBirthTextField,
            genderText, genderButtons, saveButton, datePicker) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        Row(
            modifier = Modifier
                .height(56.dp)
                .constrainAs(topPanel) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            ClickableIcon(
                painter = painterResource(R.drawable.back_arrow_icon),
                contentDescription = stringResource(R.string.back_button),
                onClick = { viewModel.onBack() }
            )

            Spacer(modifier = Modifier.weight(1f))

            HeadingMediumText(text = stringResource(R.string.account_text))

            Spacer(modifier = Modifier.weight(1.15f))
        }

        ProfileAvatar(
            imageUrl = uiState.profileImageSource.toUiSource(),
            contentDescription = stringResource(R.string.profile_image),
            size = 120.dp,
            modifier = Modifier
                .constrainAs(profileImage) {
                    centerHorizontallyTo(parent)
                    top.linkTo(topPanel.bottom, margin = 24.dp)
                }
        )

        CustomCircleIconButton(
            size = 35.dp,
            painter = painterResource(R.drawable.edit_icon),
            contentDescription = stringResource(R.string.edit_profile),
            onClick = debounce { imagePickerLauncher.launch("image/*") },
            modifier = Modifier
                .constrainAs(editProfileImageButton) {
                    bottom.linkTo(profileImage.bottom)
                    end.linkTo(profileImage.end)
                }
        )

        BodyMediumText(
            modifier = Modifier
                .constrainAs(nameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(profileImage.bottom, margin = 32.dp)
                },
            text = stringResource(R.string.full_name),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
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

        BodyMediumText(
            text = stringResource(R.string.nick_name),
            modifier = Modifier
                .constrainAs(userNameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(nameTextField.bottom, margin = 28.dp)
                },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )

        CustomTextField(
            value = uiState.nickName,
            onValueChange = viewModel::onNickNameChanged,
            hint = stringResource(R.string.nick_name_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(userNameTextField) {
                    linkTo(start = startGuideline, end = endGuideline)
                    top.linkTo(userNameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        BodyMediumText(
            text = stringResource(R.string.region),
            modifier = Modifier
                .constrainAs(regionText) {
                    start.linkTo(startGuideline)
                    top.linkTo(userNameTextField.bottom, margin = 28.dp)
                },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )

        SingleActionText(
            value = uiState.region,
            hint = stringResource(R.string.region_hint),
            isError = false,
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

        AppDropdownMenu(
            expanded = uiState.showRegionMenu,
            items = uiState.regionLocales,
            onDismiss = { viewModel.showCountryMenu(false) },
            itemContent = { regionLocale ->
                Text(regionLocale)
            },
            onItemClick = { regionLocale ->
                viewModel.onCountryChange(regionLocale)
            }
        )

        BodyMediumText(
            text = stringResource(R.string.date_of_birth),
            modifier = Modifier
                .constrainAs(dateBirthText) {
                    start.linkTo(startGuideline)
                    top.linkTo(regionTextField.bottom, margin = 28.dp)
                },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )

        SingleActionText(
            value = uiState.dateOfBirth?.toFormatedDate() ?: "",
            hint = stringResource(R.string.date_of_birth_hint),
            isError = false,
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

        BodyMediumText(
            text = stringResource(R.string.gender),
            modifier = Modifier
                .constrainAs(genderText) {
                    start.linkTo(startGuideline)
                    top.linkTo(dateBirthTextField.bottom, margin = 28.dp)
                },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
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
            onClick = { viewModel.onSaveClick() },
            text = stringResource(R.string.save_change),
            isLoading = uiState.isSaving,
            modifier = Modifier.constrainAs(saveButton) {
                linkTo(start = startGuideline, end = endGuideline)
                top.linkTo(genderButtons.bottom, margin = 40.dp)
                width = Dimension.fillToConstraints
            })
    }
}