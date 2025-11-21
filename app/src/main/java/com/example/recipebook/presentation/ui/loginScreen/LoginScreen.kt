package com.example.recipebook.presentation.ui.loginScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.recipebook.presentation.ui.commonUi.ClickableText
import com.example.recipebook.presentation.ui.commonUi.ClickableTextCheckbox
import com.example.recipebook.presentation.ui.commonUi.CustomPasswordTextField
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.HeadingText
import com.example.recipebook.presentation.ui.commonUi.MixedClickableText
import com.example.recipebook.presentation.ui.commonUi.SubHeadingText
import com.example.recipebook.presentation.ui.commonUi.TextDivider
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.viewModel.loginScreen.LoginViewModel

@Composable
@Suppress("FunctionName")
fun LoginScreen(
    onHomeScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()) {

    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isSignedIn) {
        if (uiState.isSignedIn) {
            onHomeScreen()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (headingText, subHeadingText, emailText, emailTextField,
                passwordText, passwordTextField, forgotPasswordText, rememberCheckbox,
                loginButton, dontHaveAccountText, textDivider, googleSignInButton,
                facebookSignInButton, errorSampleText) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            HeadingText(
                text = stringResource(R.string.welcome),
                modifier = Modifier.constrainAs(headingText) {
                    start.linkTo(startGuideline)
                    top.linkTo(parent.top)
                })

            SubHeadingText(
                text = stringResource(R.string.welcome_subheading),
                modifier = Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom)
                    }
                    .padding(top = 12.dp))

            TitleText(
                text = stringResource(R.string.email),
                modifier = Modifier
                    .constrainAs(emailText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom)
                    }
                    .padding(top = 32.dp))

            CustomTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChanged,
                hint = stringResource(R.string.email_hint),
                modifier = Modifier
                    .constrainAs(emailTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(emailText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 8.dp))

            TitleText(
                text = stringResource(R.string.password),
                modifier = Modifier
                    .constrainAs(passwordText) {
                        start.linkTo(startGuideline)
                        top.linkTo(emailTextField.bottom)
                    }
                    .padding(top = 20.dp))

            CustomPasswordTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                hint = stringResource(R.string.password_hint),
                modifier = Modifier
                    .constrainAs(passwordTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 8.dp),
                visible = uiState.passwordVisibility,
                changeVisibility = { viewModel.onPasswordVisibilityChange(!uiState.passwordVisibility) })

            ClickableTextCheckbox(
                checked = uiState.isRememberMeChecked,
                onValueChange = { viewModel.onRememberMeChecked(!uiState.isRememberMeChecked) },
                modifier = Modifier
                    .constrainAs(rememberCheckbox) {
                        start.linkTo(startGuideline)
                        top.linkTo(passwordTextField.bottom)
                    }
                    .padding(top = 20.dp)
            )

            ClickableText(
                "Forgot password?",
                modifier = Modifier
                    .constrainAs(forgotPasswordText) {
                        end.linkTo(endGuideline)
                        top.linkTo(passwordTextField.bottom)
                    }
                    .padding(top = 25.dp, end = 5.dp))

            SquareRoundedButton(
                onClick = { viewModel.signIn() },
                text = stringResource(R.string.sign_in_button),
                containerColor = null,
                isLoading = uiState.isLoading,
                modifier = Modifier
                    .constrainAs(loginButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(rememberCheckbox.bottom)
                    }
                    .padding(top = 32.dp)
            )

            MixedClickableText(
                simpleText = stringResource(R.string.dont_have_account),
                clickableText = stringResource(R.string.create_account),
                modifier = Modifier
                    .constrainAs(dontHaveAccountText) {
                        start.linkTo(startGuideline)
                        top.linkTo(loginButton.bottom)
                    }
                    .padding(top = 21.dp)
            )

            TextDivider(
                modifier = Modifier
                    .constrainAs(textDivider) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(dontHaveAccountText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 24.dp))

            OutlinedIconButton(
                onClick = {},
                text = stringResource(R.string.google_sign_in),
                icon = painterResource(R.drawable.google_icon),
                modifier = Modifier
                    .constrainAs(googleSignInButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(textDivider.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 24.dp)
            )

            OutlinedIconButton(
                onClick = {},
                text = stringResource(R.string.facebook_sign_in),
                icon = painterResource(R.drawable.facebook_icon),
                modifier = Modifier
                    .constrainAs(facebookSignInButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(googleSignInButton.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 20.dp)
            )
//TODO убрать заглушку, поставить snackbar
            Text(
                text = uiState.errorMessage ?: "ok",
                modifier = Modifier.constrainAs(errorSampleText) {
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    bottom.linkTo(parent.bottom)
                })
        }
    }
}
