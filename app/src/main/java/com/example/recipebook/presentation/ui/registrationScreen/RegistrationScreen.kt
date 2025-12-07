package com.example.recipebook.presentation.ui.registrationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.controller.LocalSnackBarController
import com.example.recipebook.presentation.ui.commonUi.CustomPasswordTextField
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.MixedClickableText
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.SubHeadingTextSmall
import com.example.recipebook.presentation.ui.commonUi.TextDivider
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.viewModel.model.UiEvent
import com.example.recipebook.presentation.viewModel.registrationScreen.RegistrationViewModel

@Composable
@Suppress
fun RegistrationScreen(
    onHomeScreen: () -> Unit,
    onLoginScreen: () -> Unit,
    onPrivacyScreen: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val snackBar = LocalSnackBarController.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.ShowMessage -> {
                    snackBar.showMessage(event.message)
                }
            }
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (headingText, subHeadingText, fullNameText, nameTextField,
            emailText, emailTextField, passwordText, passwordTextField,
            signUpButton, privacyText, textDivider, googleSignUpButton,
            facebookSignUpButton, emailErrorText, passwordErrorText) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextLarge(
            stringResource(R.string.create_account),
            modifier = Modifier
                .constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                })

        MixedClickableText(
            stringResource(R.string.fill_form),
            stringResource(R.string.already_have_account),
            onTextClicked = onLoginScreen,
            Modifier
                .constrainAs(subHeadingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(headingText.bottom, margin = 12.dp)
                })


        TitleText(
            text = stringResource(R.string.full_name),
            modifier = Modifier
                .constrainAs(fullNameText) {
                    start.linkTo(startGuideline)
                    top.linkTo(subHeadingText.bottom, margin = 32.dp)
                })

        CustomTextField(
            value = uiState.name,
            onValueChange = viewModel::onNameChanged,
            hint = stringResource(R.string.name_hint),
            isError = false,
            modifier = Modifier
                .constrainAs(nameTextField) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(fullNameText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth())

        TitleText(
            text = stringResource(R.string.email),
            modifier = Modifier
                .constrainAs(emailText) {
                    start.linkTo(startGuideline)
                    top.linkTo(nameTextField.bottom, margin = 20.dp)
                })

        CustomTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChanged,
            hint = stringResource(R.string.email_hint),
            isError = uiState.emailErrorCode != null,
            modifier = Modifier
                .constrainAs(emailTextField) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(emailText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth())

        if (uiState.emailErrorCode != null) {
            SubHeadingTextSmall(
                text = stringResource(uiState.emailErrorCode),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .constrainAs(emailErrorText) {
                        linkTo(start = startGuideline, end = endGuideline, bias = 0f)
                        top.linkTo(emailTextField.bottom, margin = 4.dp)
                    }
            )
        }

        TitleText(
            text = stringResource(R.string.password),
            modifier = Modifier
                .constrainAs(passwordText) {
                    start.linkTo(startGuideline)
                    top.linkTo(emailTextField.bottom, margin = 32.dp)
                }
        )

        CustomPasswordTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChanged,
            hint = stringResource(R.string.password_hint),
            isError = uiState.passwordErrorCode != null,
            visible = uiState.passwordVisibility,
            changeVisibility = { viewModel.onPasswordVisibilityChange(!uiState.passwordVisibility) },
            modifier = Modifier
                .constrainAs(passwordTextField) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(passwordText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
        )

        if (uiState.passwordErrorCode != null) {
            SubHeadingTextSmall(
                text = stringResource(uiState.passwordErrorCode),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .constrainAs(passwordErrorText) {
                        linkTo(start = startGuideline, end = endGuideline, bias = 0f)
                        top.linkTo(passwordTextField.bottom, margin = 4.dp)
                    }
            )
        }

        SquareRoundedButton(
            onClick = {
                viewModel.register(
                    name = uiState.name,
                    email = uiState.email,
                    password = uiState.password,
                    onSuccess = onHomeScreen
                )
            },
            text = stringResource(R.string.sign_up_button),
            containerColor = null,
            isLoading = uiState.isLoading,
            modifier = Modifier
                .constrainAs(signUpButton) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(passwordTextField.bottom, 32.dp)
                })

        MixedClickableText(
            stringResource(R.string.sign_up_agree),
            stringResource(R.string.conditions_privacy_policy),
            onTextClicked = onPrivacyScreen,
            modifier = Modifier
                .constrainAs(privacyText) {
                    start.linkTo(startGuideline)
                    top.linkTo(signUpButton.bottom, margin = 20.dp)
                })

        TextDivider(
            modifier = Modifier
                .constrainAs(textDivider) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(privacyText.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                })

        OutlinedIconButton(
            onClick = {},
            text = stringResource(R.string.google_sign_up),
            icon = painterResource(R.drawable.google_icon),
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .constrainAs(googleSignUpButton) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(textDivider.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }
        )

        OutlinedIconButton(
            onClick = {},
            text = stringResource(R.string.facebook_sign_up),
            icon = painterResource(R.drawable.facebook_icon),
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .constrainAs(facebookSignUpButton) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(googleSignUpButton.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                })
    }
}