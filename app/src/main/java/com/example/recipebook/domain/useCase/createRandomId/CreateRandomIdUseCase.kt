package com.example.recipebook.domain.useCase.createRandomId

interface CreateRandomIdUseCase {
    suspend fun execute(): String
}