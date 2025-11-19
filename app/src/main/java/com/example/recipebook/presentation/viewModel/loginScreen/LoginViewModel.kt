package com.example.recipebook.presentation.viewModel.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.LoginInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChanged(newEmail: String) {
        uiState = uiState.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword)
    }

    fun onPasswordVisibilityChange(isVisible: Boolean) {
        uiState = uiState.copy(passwordVisibility = isVisible)
    }

    fun onRememberMeChecked(isChecked: Boolean) {
        uiState.copy(isRememberMeChecked = isChecked)
    }

    fun signIn() {
        val email = uiState.email.trim()
        val password = uiState.password

        if (email.isEmpty()) {
            uiState = uiState.copy(errorMessage = "Email не должен быть пустым")
            return
        }

        if (password.length < 6) {
            uiState = uiState.copy(errorMessage = "Пароль должен содержать минимум 6 символов")
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage =null)
            val result = loginInteractor.signIn(email = email, password = password)
            uiState = if (result.isSuccess) {
                uiState.copy(isLoading = false, isSignedIn = true)
            } else {
                uiState.copy(isSignedIn = false, errorMessage = result.exceptionOrNull()?.message ?: "Ошибка авторизации")
            }
        }


    }
}