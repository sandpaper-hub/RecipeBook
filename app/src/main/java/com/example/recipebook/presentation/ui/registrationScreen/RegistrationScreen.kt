package com.example.recipebook.presentation.ui.registrationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.recipebook.presentation.ui.commonUi.HeadingLargeText
import com.example.recipebook.presentation.ui.commonUi.TextDivider
import com.example.recipebook.presentation.ui.commonUi.BodyMediumText
import com.example.recipebook.presentation.ui.commonUi.RootScaffold
import com.example.recipebook.presentation.viewModel.registrationScreen.RegistrationViewModel
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.toStringRes
import com.example.recipebook.presentation.viewModel.registrationScreen.model.RegistrationUiEvent

@Composable
@Suppress
fun RegistrationScreen(
    onHomeScreen: () -> Unit,
    onLoginScreen: () -> Unit,
    onPrivacyScreen: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    RootScaffold { innerPadding ->
        val resources = LocalResources.current
        val uiState by viewModel.uiState.collectAsState()
        val snackBar = LocalSnackBarController.current

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    is RegistrationUiEvent.NetworkError -> {
                        snackBar.showMessage(resources.getString(R.string.network_error))
                    }

                    is RegistrationUiEvent.UnknownError -> {
                        snackBar.showMessage(resources.getString(R.string.unknown_error))
                    }

                    is RegistrationUiEvent.OnHome -> onHomeScreen()
                    is RegistrationUiEvent.OnLogin -> onLoginScreen()
                    is RegistrationUiEvent.OnPrivacy -> onPrivacyScreen()
                }
            }
        }


        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val (headingText, subHeadingText, fullNameText, nameTextField,
                emailText, emailTextField, passwordText, passwordTextField,
                signUpButton, privacyText, textDivider, googleSignUpButton,
                emailErrorText, passwordErrorText) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            HeadingLargeText(
                stringResource(R.string.create_account),
                modifier = Modifier
                    .constrainAs(headingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(parent.top, margin = 24.dp)
                    })

            MixedClickableText(
                stringResource(R.string.fill_form),
                stringResource(R.string.already_have_account),
                onTextClicked = debounce { onLoginScreen() },
                Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom, margin = 12.dp)
                    })


            BodyMediumText(
                text = stringResource(R.string.full_name),
                modifier = Modifier
                    .constrainAs(fullNameText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom, margin = 32.dp)
                    },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

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

            BodyMediumText(
                text = stringResource(R.string.email),
                modifier = Modifier
                    .constrainAs(emailText) {
                        start.linkTo(startGuideline)
                        top.linkTo(nameTextField.bottom, margin = 20.dp)
                    },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            CustomTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChanged,
                hint = stringResource(R.string.email_hint),
                isError = uiState.emailError != null,
                modifier = Modifier
                    .constrainAs(emailTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(emailText.bottom, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth())

            if (uiState.emailError != null) {
                BodyMediumText(
                    text = stringResource(uiState.emailError!!.toStringRes()),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier
                        .constrainAs(emailErrorText) {
                            linkTo(start = startGuideline, end = endGuideline, bias = 0f)
                            top.linkTo(emailTextField.bottom, margin = 4.dp)
                        }
                )
            }

            BodyMediumText(
                text = stringResource(R.string.password),
                modifier = Modifier
                    .constrainAs(passwordText) {
                        start.linkTo(startGuideline)
                        top.linkTo(emailTextField.bottom, margin = 32.dp)
                    },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            CustomPasswordTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChanged,
                hint = stringResource(R.string.password_hint),
                isError = uiState.passwordError != null,
                passwordVisibility = uiState.passwordVisibility,
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

            if (uiState.passwordError != null) {
                BodyMediumText(
                    text = stringResource(uiState.passwordError!!.toStringRes()),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        MaterialTheme.colorScheme.error
                    ),
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
                        password = uiState.password
                    )
                },
                text = stringResource(R.string.sign_up_button),
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .constrainAs(signUpButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordTextField.bottom, 32.dp)
                        width = Dimension.fillToConstraints
                    })

            MixedClickableText(
                stringResource(R.string.sign_up_agree),
                stringResource(R.string.conditions_privacy_policy),
                onTextClicked = debounce { onPrivacyScreen() },
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
                onClick = {}, //TODO signUp with Google
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
        }
    }
}