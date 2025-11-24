package com.example.recipebook.domain.interactor.login

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : LoginInteractor {

    override suspend fun signIn(email: String, password: String): Result<Unit> =
        authenticationRepository.signIn(email = email, password = password)
}