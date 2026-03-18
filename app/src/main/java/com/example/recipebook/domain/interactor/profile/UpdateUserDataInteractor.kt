package com.example.recipebook.domain.interactor.profile

interface UpdateUserDataInteractor {
    suspend fun invoke(data: Map<String, Any?>, uriString: String?): Result<Unit>
}