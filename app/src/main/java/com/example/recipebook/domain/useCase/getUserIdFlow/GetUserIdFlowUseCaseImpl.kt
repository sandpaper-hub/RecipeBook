package com.example.recipebook.domain.useCase.getUserIdFlow

import com.example.recipebook.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserIdFlowUseCaseImpl @Inject constructor(
    private val profileRepository: ProfileRepository
): GetUserIdFlowUseCase {
    override fun execute(): Flow<String?> = profileRepository.currentUserUidFlow()
}