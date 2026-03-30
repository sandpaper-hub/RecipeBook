package com.example.recipebook.domain.model.error.authentication

sealed interface AuthenticationError {
    sealed interface Email : AuthenticationError {
        data object Empty : Email
        data object InvalidFormat : Email
        data object WrongEmail: Email
        data object EmailAlreadyInUse: Email
    }

    sealed interface Password: AuthenticationError{
        data object Empty: Password
        data object MinLength: Password
        data object WrongPassword: Password
    }
}