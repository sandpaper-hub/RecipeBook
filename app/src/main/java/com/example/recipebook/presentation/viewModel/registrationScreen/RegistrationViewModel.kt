package com.example.recipebook.presentation.viewModel.registrationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.recipebook.domain.interactor.RegistrationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor
) : ViewModel() {
    var name by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var passwordVisibility by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onNameChanged(newName: String) {
        name = newName
    }

    fun onEmailChanged(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChanged(newPassword: String) {
        password = newPassword
    }

    fun onPasswordVisibilityChange(newValue: Boolean) {
        passwordVisibility = newValue
    }

    fun register(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Email & password shouldn't be blank"
            return
        }

        isLoading = true
        registrationInteractor.register(
            name = name,
            email = email,
            password = password,
            onSuccess = {
                isLoading = false
                onSuccess()
            },
            onError = { message ->
                isLoading = false
                errorMessage = message
            }
        )
        errorMessage = null
    }
}
