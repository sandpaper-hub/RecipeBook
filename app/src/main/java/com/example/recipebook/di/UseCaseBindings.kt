package com.example.recipebook.di

import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCase
import com.example.recipebook.domain.useCase.createRandomId.CreateRandomIdUseCaseImpl
import com.example.recipebook.domain.useCase.getUserCollection.GetUserCollectionUseCase
import com.example.recipebook.domain.useCase.getUserCollection.GetUserCollectionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBindings {

    @Binds
    abstract fun bindCreateRandomIdUseCase(
        impl: CreateRandomIdUseCaseImpl
    ): CreateRandomIdUseCase

    @Binds
    abstract fun bindGetUserCollectionUseCase(
        impl: GetUserCollectionUseCaseImpl
    ): GetUserCollectionUseCase
}