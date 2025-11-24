package com.example.recipebook.domain.interactor.registration

interface RegistrationInteractor {
     suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<Unit>
}