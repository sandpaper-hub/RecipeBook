package com.example.recipebook.presentation.viewModel.registrationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegistrationViewModel : ViewModel() {
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

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

    fun register() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Email & password shouldn't be blank"
            return
        }

        isLoading = true
        errorMessage = null

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                isLoading = false
                if (task.isSuccessful) {
                    errorMessage = "Success"
                } else {
                    errorMessage = task.exception?.message ?: "Error"
                }
            }
    }
}
