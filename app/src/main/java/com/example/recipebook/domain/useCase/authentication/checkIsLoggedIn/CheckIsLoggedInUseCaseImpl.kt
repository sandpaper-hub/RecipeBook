package com.example.recipebook.domain.useCase.authentication.checkIsLoggedIn

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class CheckIsLoggedInUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : CheckIsLoggedInUseCase{
    override fun execute(): Boolean = authenticationRepository.isLoggedIn()
}