package com.example.recipebook.di

import com.example.recipebook.data.repository.FirebaseRepositoryImpl
import com.example.recipebook.domain.repository.FirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindings {
    @Binds
    @Singleton
    abstract fun bindFirebaseRepository(
        impl: FirebaseRepositoryImpl
    ): FirebaseRepository
}