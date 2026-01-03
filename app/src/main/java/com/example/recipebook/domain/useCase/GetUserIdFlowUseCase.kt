package com.example.recipebook.domain.useCase

import com.example.recipebook.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserIdFlowUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    fun execute(): Flow<String?> = profileRepository.currentUserUidFlow()
}