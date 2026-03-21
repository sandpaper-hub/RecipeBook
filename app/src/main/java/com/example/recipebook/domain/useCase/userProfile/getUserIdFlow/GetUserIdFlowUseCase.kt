package com.example.recipebook.domain.useCase.userProfile.getUserIdFlow

import kotlinx.coroutines.flow.Flow

interface GetUserIdFlowUseCase {
    fun execute(): Flow<String?>
}