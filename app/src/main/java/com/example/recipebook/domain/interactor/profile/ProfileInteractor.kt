package com.example.recipebook.domain.interactor.profile

import kotlinx.coroutines.flow.Flow

interface ProfileInteractor {
    fun getLocales(): List<String>
    fun getUserIdFlow(): Flow<String?>
}