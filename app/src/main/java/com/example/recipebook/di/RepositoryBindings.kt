package com.example.recipebook.di

import com.example.recipebook.data.repository.RegistrationRepositoryImpl
import com.example.recipebook.domain.repository.RegistrationRepository
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
    abstract fun bindRegistrationRepository(
        impl: RegistrationRepositoryImpl
    ): RegistrationRepository
}