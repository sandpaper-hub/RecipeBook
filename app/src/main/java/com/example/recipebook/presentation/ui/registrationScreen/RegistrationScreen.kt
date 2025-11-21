package com.example.recipebook.presentation.ui.registrationScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.commonUi.CustomPasswordTextField
import com.example.recipebook.presentation.ui.commonUi.CustomTextField
import com.example.recipebook.presentation.ui.commonUi.OutlinedIconButton
import com.example.recipebook.presentation.ui.commonUi.SquareRoundedButton
import com.example.recipebook.presentation.ui.commonUi.MixedClickableText
import com.example.recipebook.presentation.ui.commonUi.HeadingText
import com.example.recipebook.presentation.ui.commonUi.TextDivider
import com.example.recipebook.presentation.ui.commonUi.TitleText
import com.example.recipebook.presentation.viewModel.registrationScreen.RegistrationViewModel

@Composable
@Suppress
fun RegistrationScreen(
    onHomeScreen: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel()
) {

    val name = viewModel.name
    val email = viewModel.email
    val password = viewModel.password
    val passwordVisibility = viewModel.passwordVisibility
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val (headingText, subHeadingText, fullNameText, nameTextField,
                emailText, emailTextField, passwordText, passwordTextField,
                signUpButton, privacyText, textDivider, googleSignUpButton,
                facebookSignUpButton, errorText) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            HeadingText(
                stringResource(R.string.create_account),
                modifier = Modifier
                    .constrainAs(headingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(parent.top)
                    })

            MixedClickableText(
                stringResource(R.string.fill_form),
                stringResource(R.string.already_have_account),
                Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom)
                    }
                    .padding(top = 12.dp))


            TitleText(
                text = stringResource(R.string.full_name),
                modifier = Modifier
                    .constrainAs(fullNameText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom)
                    }
                    .padding(top = 32.dp))

            CustomTextField(
                value = name,
                onValueChange = viewModel::onNameChanged,
                hint = stringResource(R.string.name_hint),
                modifier = Modifier
                    .constrainAs(nameTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(fullNameText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp))

            TitleText(
                text = stringResource(R.string.email),
                modifier = Modifier
                    .constrainAs(emailText) {
                        start.linkTo(startGuideline)
                        top.linkTo(nameTextField.bottom)
                    }
                    .padding(top = 20.dp))

            CustomTextField(
                value = email,
                onValueChange = viewModel::onEmailChanged,
                hint = stringResource(R.string.email_hint),
                modifier = Modifier
                    .constrainAs(emailTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(emailText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp))

            TitleText(
                text = stringResource(R.string.password),
                modifier = Modifier
                    .constrainAs(passwordText) {
                        start.linkTo(startGuideline)
                        top.linkTo(emailTextField.bottom)
                    }
                    .padding(top = 20.dp)
            )

            CustomPasswordTextField(
                value = password,
                onValueChange = viewModel::onPasswordChanged,
                hint = stringResource(R.string.password_hint),
                visible = passwordVisibility,
                changeVisibility = { viewModel.onPasswordVisibilityChange(!passwordVisibility) },
                modifier = Modifier
                    .constrainAs(passwordTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            SquareRoundedButton(
                onClick = { viewModel.register(onSuccess = onHomeScreen) },
                text = stringResource(R.string.sign_up_button),
                containerColor = null,
                isLoading = isLoading,
                modifier = Modifier
                    .constrainAs(signUpButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordTextField.bottom)
                    }
                    .padding(top = 32.dp))

            MixedClickableText(
                stringResource(R.string.sign_up_agree),
                stringResource(R.string.conditions_privacy_policy),
                modifier = Modifier
                    .constrainAs(privacyText) {
                        start.linkTo(startGuideline)
                        top.linkTo(signUpButton.bottom)
                    }
                    .padding(top = 20.dp))

            TextDivider(
                modifier = Modifier
                    .constrainAs(textDivider) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(privacyText.bottom)
                    }
                    .padding(24.dp))

            OutlinedIconButton(
                onClick = {},
                stringResource(R.string.google_sign_up),
                painterResource(R.drawable.google_icon),
                modifier = Modifier
                    .constrainAs(googleSignUpButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(textDivider.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 24.dp)
            )

            OutlinedIconButton(
                onClick = {},
                stringResource(R.string.facebook_sign_up),
                painterResource(R.drawable.facebook_icon),
                modifier = Modifier
                    .constrainAs(facebookSignUpButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(googleSignUpButton.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .padding(top = 24.dp)
            )

            if (error != null) {
                Text(
                    text = error, modifier = Modifier
                        .height(20.dp)
                        .constrainAs(errorText) {
                            start.linkTo(startGuideline)
                            end.linkTo(endGuideline)
                            bottom.linkTo(parent.bottom)
                        })
            }
        }
    }
}