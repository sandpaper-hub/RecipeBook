package com.example.recipebook.domain.interactor.login

import com.example.recipebook.domain.useCase.LoginByEmailUseCase
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val loginByEmailUseCase: LoginByEmailUseCase
) : LoginInteractor {

    override suspend fun loginByEmail(email: String, password: String): Result<Unit> =
        loginByEmailUseCase.execute(email = email, password = password)
}