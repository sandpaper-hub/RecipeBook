package com.example.recipebook.presentation.viewModel.registrationScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.interactor.registration.RegistrationInteractor
import com.example.recipebook.domain.model.authentication.AuthenticationError
import com.example.recipebook.domain.model.authentication.AuthenticationException
import com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput.ValidateAuthenticationInputUseCase
import com.example.recipebook.presentation.viewModel.registrationScreen.model.RegistrationUiEvent
import com.example.recipebook.presentation.viewModel.registrationScreen.model.RegistrationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationInteractor: RegistrationInteractor,
    private val validateAuthenticationInputUseCase: ValidateAuthenticationInputUseCase

) : ViewModel() {
    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegistrationUiEvent>()
    val events: SharedFlow<RegistrationUiEvent> = _events

    fun onNameChanged(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
    }

    fun onEmailChanged(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail, emailError = null)
        }
    }

    fun onPasswordChanged(newPassword: String) {
        _uiState.update {
            it.copy(password = newPassword, passwordError = null)
        }
    }

    fun onPasswordVisibilityChange(newValue: Boolean) {
        _uiState.update {
            it.copy(passwordVisibility = newValue)
        }
    }

    fun register(
        name: String,
        email: String,
        password: String,
    ) {
        val emailError = validateAuthenticationInputUseCase.validateEmail(email)
        val passwordError = validateAuthenticationInputUseCase.validatePassword(password)

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            registrationInteractor.register(
                name = name,
                email = email,
                password = password
            ).fold(
                onSuccess = {
                    _events.emit(RegistrationUiEvent.OnHome)
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    when (exception) {
                        is AuthenticationException.EmailAlreadyInUse -> {
                            _uiState.update {
                                it.copy(emailError = AuthenticationError.Email.EmailAlreadyInUse)
                            }
                        }

                        is AuthenticationException.NetworkException -> {
                            _events.emit(RegistrationUiEvent.NetworkError)
                        }

                        is AuthenticationException.InvalidEmail -> {
                            _uiState.update {
                                it.copy(emailError = AuthenticationError.Email.InvalidFormat)
                            }
                        }

                        else -> {
                            _events.emit(RegistrationUiEvent.UnknownError)
                        }
                    }
                })
        }
    }
}