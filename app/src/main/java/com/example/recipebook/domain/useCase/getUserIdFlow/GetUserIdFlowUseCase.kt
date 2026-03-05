package com.example.recipebook.domain.useCase.getUserIdFlow

import kotlinx.coroutines.flow.Flow

interface GetUserIdFlowUseCase {
    fun execute(): Flow<String?>
}