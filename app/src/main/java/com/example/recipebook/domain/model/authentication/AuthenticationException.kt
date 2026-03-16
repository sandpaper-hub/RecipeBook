package com.example.recipebook.domain.model.authentication

sealed class AuthenticationException: Exception() {
    class NetworkException: AuthenticationException()
    class WrongPassword: AuthenticationException()
    class EmailAlreadyInUse: AuthenticationException()
    class InvalidEmail: AuthenticationException()
    class Unknown: AuthenticationException()
}