package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    fun execute(): String {
        return authenticationRepository.getCurrentUserId()
            ?: throw IllegalStateException("User not authenticated")
    }
}