package com.example.recipebook.presentation.ui.loginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.controller.LocalSnackBarController
import com.example.recipebook.presentation.ui.commonUi.ClickableText
import com.example.recipebook.presentation.ui.commonUi.CustomPasswordTextField
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.HeadingLargeText
import com.example.recipebook.presentation.ui.commonUi.MixedClickableText
import com.example.recipebook.presentation.ui.commonUi.TextDivider
import com.example.recipebook.presentation.ui.commonUi.BodyMediumText
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.RootScaffold
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.viewModel.loginScreen.LoginViewModel
import com.example.recipebook.presentation.util.debounce
import com.example.recipebook.presentation.util.toStringRes
import com.example.recipebook.presentation.viewModel.loginScreen.model.LoginUiEvent

@Composable
@Suppress("FunctionName")
fun LoginScreen(
    onHomeScreen: () -> Unit,
    onRegistrationScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    RootScaffold { innerPadding ->
        val snackbar = LocalSnackBarController.current
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                when (event) {
                    LoginUiEvent.NetworkError -> {
                        snackbar.showMessage(message = "Network error")
                    }

                    LoginUiEvent.UnknownError -> {
                        snackbar.showMessage(message = "Неизвестная ошибка")//TODO
                    }

                    LoginUiEvent.OnHomeScreen -> {
                        onHomeScreen()
                    }
                }
            }
        }

        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            val (headingText, subHeadingText, emailText, emailTextField,
                passwordText, passwordTextField, forgotPasswordText,
                loginButton, dontHaveAccountText, textDivider, googleSignInButton,
                emailErrorText, passwordErrorText) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            HeadingLargeText(
                text = stringResource(R.string.welcome),
                modifier = Modifier.constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top, margin = 24.dp)
                })

            BodyMediumText(
                text = stringResource(R.string.welcome_subheading),
                style = MaterialTheme.typography.bodyMedium.copy(
                    MaterialTheme.colorScheme.inversePrimary
                ),
                modifier = Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom, margin = 12.dp)
                    })

            BodyMediumText(
                text = stringResource(R.string.email),
                modifier = Modifier
                    .constrainAs(emailText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom, margin = 32.dp)
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
                    })

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
                onValueChange = viewModel::onPasswordChange,
                hint = stringResource(R.string.password_hint),
                isError = uiState.passwordError != null,
                modifier = Modifier
                    .constrainAs(passwordTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordText.bottom, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    },
                passwordVisibility = uiState.passwordVisibility,
                changeVisibility = { viewModel.onPasswordVisibilityChange(!uiState.passwordVisibility) })

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

            ClickableText(
                clickableText = stringResource(R.string.forgot_password),
                onClick = debounce { }, //TODO to forgot password
                modifier = Modifier
                    .constrainAs(forgotPasswordText) {
                        end.linkTo(endGuideline)
                        top.linkTo(passwordTextField.bottom, margin = 25.dp)
                    })

            SquareRoundedButton(
                onClick = { viewModel.signIn() }, //TODO block button
                text = stringResource(R.string.sign_in_button),
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .constrainAs(loginButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(forgotPasswordText.bottom, margin = 32.dp)
                        width = Dimension.fillToConstraints
                    }
            )

            MixedClickableText(
                simpleText = stringResource(R.string.dont_have_account),
                clickableText = stringResource(R.string.create_account),
                onTextClicked = debounce { onRegistrationScreen() },
                modifier = Modifier
                    .constrainAs(dontHaveAccountText) {
                        start.linkTo(startGuideline)
                        top.linkTo(loginButton.bottom, margin = 21.dp)
                    }
            )

            TextDivider(
                modifier = Modifier
                    .constrainAs(textDivider) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(dontHaveAccountText.bottom, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    })

            OutlinedIconButton(
                onClick = debounce { }, //TODO signIn with google
                text = stringResource(R.string.google_sign_in),
                textColor = MaterialTheme.colorScheme.onPrimary,
                icon = painterResource(R.drawable.google_icon),
                modifier = Modifier
                    .constrainAs(googleSignInButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(textDivider.bottom, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    }
            )
        }
    }
}