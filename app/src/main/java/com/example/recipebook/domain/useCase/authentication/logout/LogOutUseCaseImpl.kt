package com.example.recipebook.domain.useCase.authentication.logout

import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class LogOutUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val dataStoreRepository: DataStoreRepository
): LogOutUseCase {
    override suspend fun execute() {
        authenticationRepository.logOut()
        dataStoreRepository.clearUserData()
    }
}