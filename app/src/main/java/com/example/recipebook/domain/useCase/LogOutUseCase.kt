package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.domain.repository.DataStoreRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend fun execute() {
        authenticationRepository.logOut()
        dataStoreRepository.clearUserData()
    }
}