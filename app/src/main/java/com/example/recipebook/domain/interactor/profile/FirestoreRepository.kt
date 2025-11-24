package com.example.recipebook.domain.interactor.profile

import com.example.recipebook.domain.model.UserProfile

interface FirestoreRepository {
    suspend fun createUserDocumentIfNeeded(userProfile: UserProfile): Result<Unit>
    suspend fun getUserProfile(uid: String): Result<UserProfile>
}