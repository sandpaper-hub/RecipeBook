package com.example.recipebook.presentation.viewModel.registrationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.registration.RegistrationInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            if (email.isBlank() || password.isBlank()) {
                errorMessage = "Email & password shouldn't be blank"
                return@launch
            }
            isLoading = true
            val result = registrationInteractor.register(
                name = name,
                email = email,
                password = password
            )

            result
                .onSuccess {
                    isLoading = false
                    onSuccess()
                }
                .onFailure { exception ->
                    isLoading = false
                    errorMessage = exception.message
                }
        }
    }
}