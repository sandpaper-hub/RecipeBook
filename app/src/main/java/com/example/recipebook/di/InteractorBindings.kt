package com.example.recipebook.di

import com.example.recipebook.domain.interactor.login.LoginInteractor
import com.example.recipebook.domain.interactor.login.LoginInteractorImpl
import com.example.recipebook.domain.interactor.registration.RegistrationInteractor
import com.example.recipebook.domain.interactor.registration.RegistrationInteractorImpl
import com.example.recipebook.domain.interactor.splash.SplashInteractor
import com.example.recipebook.domain.interactor.splash.SplashInteractorImpl
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

    @Binds
    abstract fun bindLoginInteractor(
        impl: LoginInteractorImpl
    ): LoginInteractor

    @Binds
    abstract fun bindSplashInteractor(
        impl: SplashInteractorImpl
    ): SplashInteractor
}