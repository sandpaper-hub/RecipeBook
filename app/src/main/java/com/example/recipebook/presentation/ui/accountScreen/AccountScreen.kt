package com.example.recipebook.presentation.ui.accountScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.domain.Constraints
import com.example.recipebook.domain.model.error.validation.ValidationError
import com.example.recipebook.presentation.controller.LocalSnackBarController
import com.example.recipebook.presentation.ui.commonUi.DatePickerDialog
import com.example.recipebook.presentation.ui.commonUi.SelectableButtonBox
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.AppDropdownMenu
import com.example.recipebook.presentation.ui.commonUi.EditProfileAvatar
import com.example.recipebook.presentation.ui.commonUi.LimitedTextFieldBox
import com.example.recipebook.presentation.ui.commonUi.SingleActionTextBox
import com.example.recipebook.presentation.ui.commonUi.TopBarTitle
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
    val snackBarController = LocalSnackBarController.current
    val uiState by viewModel.uiState.collectAsState()
    val genderOptions = listOf("Male", "Female")
    val resources = LocalResources.current
    val verticalScrollState = rememberScrollState()
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
                AccountUiEvent.NoSpecificSymbol -> snackBarController.showMessage(
                    resources.getString(
                        R.string.no_specific_symbols_error
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxSize()
    ) {

        TopBarTitle(
            onBackClick = onBackNavigation,
            title = stringResource(R.string.account_text)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1f)
                .verticalScroll(verticalScrollState)
        ) {

            EditProfileAvatar(
                imageUrl = uiState.profileImageSource.toUiSource(),
                onClick = debounce { imagePickerLauncher.launch("image/*") }
            )

            LimitedTextFieldBox(
                title = stringResource(R.string.full_name),
                textFieldValue = uiState.fullName.value,
                onValueChange = viewModel::onNameChanged,
                onClearText = { viewModel.onNameChanged("") },
                errorText = when (uiState.fullName.error) {
                    is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                    is ValidationError.MaxSymbolLimit -> stringResource(
                        R.string.max_symbols_limit,
                        Constraints.MAX_FULL_NAME_LENGTH
                    )

                    else -> null
                },
                textLengthLimit = Constraints.MAX_FULL_NAME_LENGTH,
                textHint = stringResource(R.string.name_hint)
            )


            LimitedTextFieldBox(
                title = stringResource(R.string.nick_name),
                textFieldValue = uiState.nickName.value,
                onValueChange = viewModel::onNickNameChanged,
                onClearText = { viewModel.onNickNameChanged("") },
                errorText = when (uiState.nickName.error) {
                    is ValidationError.Empty -> stringResource(R.string.field_cant_be_blank)
                    is ValidationError.MaxSymbolLimit -> stringResource(
                        R.string.max_symbols_limit,
                        Constraints.MAX_NICKNAME_LENGTH
                    )
                    is ValidationError.MinSymbolLimit -> stringResource(
                        R.string.min_symbols_limit, Constraints.MIN_NICKNAME_LENGTH
                    )

                    else -> null
                },
                textLengthLimit = Constraints.MAX_NICKNAME_LENGTH,
                textHint = stringResource(R.string.nick_name_hint)
            )

            SingleActionTextBox(
                title = stringResource(R.string.region),
                value = uiState.region,
                hint = stringResource(R.string.region_hint),
                errorText = null,
                contentDescription = stringResource(R.string.region),
                onClick = { viewModel.showCountryMenu(true) },
                painter = null
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

            SingleActionTextBox(
                title = stringResource(R.string.date_of_birth),
                value = uiState.dateOfBirth?.toFormatedDate() ?: "",
                hint = stringResource(R.string.date_of_birth_hint),
                errorText = null,
                contentDescription = stringResource(R.string.date_of_birth),
                onClick = { viewModel.showDatePicker(true) },
                painter = painterResource(R.drawable.date_icon)
            )

            SelectableButtonBox(
                values = genderOptions,
                selectedValue = uiState.gender,
                onValueSelected = viewModel::onGenderChanged,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.weight(1f))

            SquareRoundedButton(
                onClick = { viewModel.onSaveClick() },
                text = stringResource(R.string.save_change),
                isLoading = uiState.isSaving,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            DatePickerDialog(
                isOpen = uiState.showDatePicker,
                onConfirm = viewModel::selectConfirmedDate,
                onCancel = { viewModel.showDatePicker(false) },
                modifier = Modifier
            )
        }
    }
}