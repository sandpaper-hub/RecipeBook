package com.example.recipebook.domain.interactor.registration

import com.example.recipebook.domain.useCase.authentication.RegistrationByEmailUseCase
import javax.inject.Inject

class RegistrationInteractorImpl @Inject constructor(
    private val registrationByEmailUseCase: RegistrationByEmailUseCase
) : RegistrationInteractor {
    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit> = registrationByEmailUseCase.execute(name, email, password)
}