package com.example.recipebook.presentation.viewModel.registrationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisibility by mutableStateOf(false)
        private set
    var isRememberMeChecked by mutableStateOf(false)
        private set

    fun onEmailChanged(newEmail: String){
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onPasswordVisibilityChange(isVisible: Boolean) {
        passwordVisibility = isVisible
    }

    fun onRememberMeChecked(isChecked: Boolean) {
        isRememberMeChecked = isChecked
    }
}