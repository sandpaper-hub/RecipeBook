package com.example.recipebook.presentation.viewModel.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.R
import com.example.recipebook.domain.interactor.login.LoginInteractor
import com.example.recipebook.presentation.viewModel.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginInteractor: LoginInteractor
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    private val _events = MutableSharedFlow<UiEvent>()
    val events: SharedFlow<UiEvent> = _events
    fun onEmailChanged(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailErrorMessageCode = null)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordErrorMessageCode = null)
    }

    fun onPasswordVisibilityChange(isVisible: Boolean) {
        uiState = uiState.copy(passwordVisibility = isVisible)
    }

    fun signIn() {
        val email = uiState.email.trim()
        val password = uiState.password


        uiState = uiState.copy(
            emailErrorMessageCode = when {
                uiState.email.isBlank() -> R.string.blank_email
                else -> null
            },
            passwordErrorMessageCode = when {
                uiState.password.isBlank() || uiState.password.length < 6 ->
                    R.string.password_min_digit

                else -> null
            }
        )
        if (uiState.emailErrorMessageCode != null || uiState.passwordErrorMessageCode != null) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, snackBarMessage = null)
            val result = loginInteractor.signIn(email = email, password = password)
            if (result.isSuccess) {
                uiState = uiState.copy(isLoading = false, isSignedIn = true)
            } else {
                uiState = uiState.copy(isLoading = false, isSignedIn = false)
                _events.emit(
                    UiEvent.ShowMessage(result.exceptionOrNull()?.message ?: "Ошибка авторизации")
                )
            }
        }
    }
}