package com.example.recipebook.domain.interactor

import com.example.recipebook.domain.repository.FirebaseRepository
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : LoginInteractor {

    override suspend fun signIn(email: String, password: String): Result<Unit> =
        firebaseRepository.signIn(email = email, password = password)
}