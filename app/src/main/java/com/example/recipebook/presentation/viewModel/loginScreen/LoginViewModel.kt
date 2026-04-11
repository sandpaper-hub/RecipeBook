package com.example.recipebook.presentation.viewModel.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipebook.domain.model.error.authentication.AuthenticationException
import com.example.recipebook.domain.model.error.authentication.AuthenticationError
import com.example.recipebook.domain.useCase.authentication.loginByEmail.LoginByEmailUseCase
import com.example.recipebook.domain.useCase.authentication.validateAuthenticationInput.ValidateAuthenticationInputUseCase
import com.example.recipebook.presentation.viewModel.loginScreen.model.LoginUiEvent
import com.example.recipebook.presentation.viewModel.loginScreen.model.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginByEmailUseCase: LoginByEmailUseCase,
    private val validateAuthenticationInputUseCase: ValidateAuthenticationInputUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events = _events.asSharedFlow()
    fun onEmailChanged(newEmail: String) {
        _uiState.update {
            it.copy(
                email = newEmail,
                emailError = null
            )
        }
    }

    fun onPasswordChange(newPassword: String) {
        _uiState.update {
            it.copy(
                password = newPassword,
                passwordError = null
            )
        }
    }

    fun onPasswordVisibilityChange(isVisible: Boolean) {
        _uiState.update {
            it.copy(passwordVisibility = isVisible)
        }
    }

    fun signIn() {
        val emailError = validateAuthenticationInputUseCase.validateEmail(_uiState.value.email)
        val passwordError = validateAuthenticationInputUseCase.validatePassword(_uiState.value.password)

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
            _uiState.update {
                it.copy(isLoading = true)
            }

            loginByEmailUseCase.execute(
                email = _uiState.value.email,
                password = _uiState.value.password
            )
                .onSuccess {
                    _events.emit(LoginUiEvent.OnHomeScreen)
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                    when (exception) {
                        is AuthenticationException.NetworkException -> {
                            _events.emit(LoginUiEvent.NetworkError)
                        }

                        is AuthenticationException.WrongPassword -> {
                            _uiState.update {
                                it.copy(
                                    emailError = AuthenticationError.Email.WrongEmail,
                                    passwordError = AuthenticationError.Password.WrongPassword
                                )
                            }
                        }

                        else -> {
                            _events.emit(LoginUiEvent.UnknownError)
                        }
                    }
                }
        }
    }
}