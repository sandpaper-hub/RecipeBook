package com.example.recipebook.presentation.viewModel.registrationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.R
import com.example.recipebook.domain.interactor.registration.RegistrationInteractor
import com.example.recipebook.presentation.viewModel.model.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor
) : ViewModel() {
    var uiState by mutableStateOf(RegistrationState())
        private set
    private val _events = MutableSharedFlow<UiEvent>()
    val events: SharedFlow<UiEvent> = _events

    fun onNameChanged(newName: String) {
        uiState = uiState.copy(name = newName)
    }

    fun onEmailChanged(newEmail: String) {
        uiState = uiState.copy(email = newEmail, emailErrorCode = null)
    }

    fun onPasswordChanged(newPassword: String) {
        uiState = uiState.copy(password = newPassword, passwordErrorCode = null)
    }

    fun onPasswordVisibilityChange(newValue: Boolean) {
        uiState = uiState.copy(passwordVisibility = newValue)
    }

    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        uiState = uiState.copy(
            emailErrorCode = when {
                uiState.email.isBlank() -> R.string.blank_email
                else -> null
            },
            passwordErrorCode = when {
                uiState.password.isBlank() || uiState.password.length < 6 ->
                    R.string.password_min_digit

                else -> null
            }
        )
        if (uiState.emailErrorCode != null || uiState.passwordErrorCode != null) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = registrationInteractor.register(
                name = name,
                email = email,
                password = password
            )

            result
                .onSuccess {
                    uiState = uiState.copy(isLoading = false)
                    onSuccess()
                }
                .onFailure { exception ->
                    uiState = uiState.copy(isLoading = false)
                    showSnackBar(exception.message)
                }
        }
    }

    private suspend fun showSnackBar(message: String?) {
        _events.emit(
            UiEvent.ShowMessage(message)
        )
    }
}