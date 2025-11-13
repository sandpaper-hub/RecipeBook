package com.example.recipebook.presentation.ui.registrationScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipebook.R
import com.example.recipebook.presentation.ui.CustomPasswordTextField
import com.example.recipebook.presentation.ui.CustomTextField
import com.example.recipebook.presentation.ui.OutlinedIconButton
import com.example.recipebook.presentation.ui.SquareRoundedButton
import com.example.recipebook.presentation.ui.ClickableText
import com.example.recipebook.presentation.ui.TextDivider
import com.example.recipebook.presentation.viewModel.RegistrationViewModel

@Composable
@Suppress
fun RegistrationScreen(viewModel: RegistrationViewModel = viewModel()) {

    val name = viewModel.name
    val email = viewModel.email
    val password = viewModel.password
    val passwordVisibility = viewModel.passwordVisibility

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            val (headingText, subHeadingText, fullNameText, nameTextField,
                emailText, emailTextField, passwordText, passwordTextField,
                signUpButton, privacyText, textDivider, googleSignUpButton,
                facebookSignUpButton) = createRefs()
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            Text(
                "Create Account", modifier = Modifier
                    .constrainAs(headingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(parent.top)
                    }
                    .padding(top = 24.dp), style = MaterialTheme.typography.headlineMedium)

            ClickableText(
                "Enter your name, email and password\nfor sign up. ",
                "Already have account?",
                Modifier
                    .constrainAs(subHeadingText) {
                        start.linkTo(startGuideline)
                        top.linkTo(headingText.bottom)
                    }
                    .padding(top = 12.dp))

            Text(
                "Full Name", modifier = Modifier
                    .constrainAs(fullNameText) {
                        start.linkTo(startGuideline)
                        top.linkTo(subHeadingText.bottom)
                    }
                    .padding(top = 32.dp), style = MaterialTheme.typography.bodyMedium)

            CustomTextField(
                value = name,
                onValueChange = viewModel::onNameChanged,
                hint = "Constantine Kim...",
                modifier = Modifier
                    .constrainAs(nameTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(fullNameText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp))

            Text(
                "Email Address", modifier = Modifier
                    .constrainAs(emailText) {
                        start.linkTo(startGuideline)
                        top.linkTo(nameTextField.bottom)
                    }
                    .padding(top = 20.dp), style = MaterialTheme.typography.bodyMedium)

            CustomTextField(
                value = email,
                onValueChange = viewModel::onEmailChanged,
                hint = "recipe@book.com",
                modifier = Modifier
                    .constrainAs(emailTextField) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(emailText.bottom)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .padding(top = 8.dp))

            Text(
                "Password", modifier = Modifier
                    .constrainAs(passwordText) {
                        start.linkTo(startGuideline)
                        top.linkTo(emailTextField.bottom)
                    }
                    .padding(top = 20.dp), style = MaterialTheme.typography.bodyMedium)

            CustomPasswordTextField(
                value = password,
                onValueChange = viewModel::onPasswordChanged,
                hint = "Input password",
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
                onClick = {},
                text = "Sign Up",
                containerColor = null,
                modifier = Modifier
                    .constrainAs(signUpButton) {
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        top.linkTo(passwordTextField.bottom)
                    }
                    .padding(top = 32.dp))

            ClickableText(
                "By signing up you're agree to our ",
                "Terms Conditions\n & Privacy Policy",
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
                "Sign Up With Google",
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
                "Sign Up With Facebook",
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
        }
    }
}