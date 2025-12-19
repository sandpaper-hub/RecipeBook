package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckIsLoggedInUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(): Boolean = authenticationRepository.isLoggedIn()
}