package com.example.recipebook.presentation.ui.loginScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.recipebook.presentation.ui.commonUi.ClickableText
import com.example.recipebook.presentation.ui.commonUi.CustomPasswordTextField
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.HeadingTextLarge
import com.example.recipebook.presentation.ui.commonUi.MixedClickableText
import com.example.recipebook.presentation.ui.commonUi.SubHeadingTextSmall
import com.example.recipebook.presentation.ui.commonUi.TextDivider
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.viewModel.loginScreen.LoginViewModel
import com.example.recipebook.presentation.viewModel.model.UiEvent

@Composable
@Suppress("FunctionName")
fun LoginScreen(
    onHomeScreen: () -> Unit,
    onRegistrationScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState
    val snackBar = LocalSnackBarController.current

    LaunchedEffect(uiState.isSignedIn) {
        if (uiState.isSignedIn) {
            onHomeScreen()
        }
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
        val (headingText, subHeadingText, emailText, emailTextField,
            passwordText, passwordTextField, forgotPasswordText,
            loginButton, dontHaveAccountText, textDivider, googleSignInButton,
            facebookSignInButton, errorSampleText) = createRefs()
        val startGuideline = createGuidelineFromStart(24.dp)
        val endGuideline = createGuidelineFromEnd(24.dp)

        HeadingTextLarge(
            text = stringResource(R.string.welcome),
            modifier = Modifier.constrainAs(headingText) {
                start.linkTo(startGuideline)
                top.linkTo(parent.top, margin = 24.dp)
            })

        SubHeadingTextSmall(
            text = stringResource(R.string.welcome_subheading),
            modifier = Modifier
                .constrainAs(subHeadingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(headingText.bottom, margin = 12.dp)
                })

        TitleText(
            text = stringResource(R.string.email),
            modifier = Modifier
                .constrainAs(emailText) {
                    start.linkTo(startGuideline)
                    top.linkTo(subHeadingText.bottom, margin = 32.dp)
                })

        CustomTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChanged,
            hint = stringResource(R.string.email_hint),
            modifier = Modifier
                .constrainAs(emailTextField) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(emailText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                })

        TitleText(
            text = stringResource(R.string.password),
            modifier = Modifier
                .constrainAs(passwordText) {
                    start.linkTo(startGuideline)
                    top.linkTo(emailTextField.bottom, margin = 20.dp)
                })

        CustomPasswordTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            hint = stringResource(R.string.password_hint),
            modifier = Modifier
                .constrainAs(passwordTextField) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(passwordText.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
            visible = uiState.passwordVisibility,
            changeVisibility = { viewModel.onPasswordVisibilityChange(!uiState.passwordVisibility) })

        ClickableText(
            clickableText = stringResource(R.string.forgot_password),
            modifier = Modifier
                .constrainAs(forgotPasswordText) {
                    end.linkTo(endGuideline)
                    top.linkTo(passwordTextField.bottom, margin = 25.dp)
                })

        SquareRoundedButton(
            onClick = { viewModel.signIn() },
            text = stringResource(R.string.sign_in_button),
            containerColor = null,
            isLoading = uiState.isLoading,
            modifier = Modifier
                .constrainAs(loginButton) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(forgotPasswordText.bottom, margin = 32.dp)
                }
        )

        MixedClickableText(
            simpleText = stringResource(R.string.dont_have_account),
            clickableText = stringResource(R.string.create_account),
            onTextClicked = onRegistrationScreen,
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
            onClick = {},
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

        OutlinedIconButton(
            onClick = {},
            text = stringResource(R.string.facebook_sign_in),
            textColor = MaterialTheme.colorScheme.onPrimary,
            icon = painterResource(R.drawable.facebook_icon),
            modifier = Modifier
                .constrainAs(facebookSignInButton) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    top.linkTo(googleSignInButton.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                }
        )
    }
}
