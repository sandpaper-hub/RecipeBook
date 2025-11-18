package com.example.recipebook.di

import com.example.recipebook.domain.interactor.RegistrationInteractor
import com.example.recipebook.domain.interactor.RegistrationInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorBindings {


    @Binds
    abstract fun bingRegistrationInteractor(
        impl: RegistrationInteractorImpl
    ): RegistrationInteractor
}