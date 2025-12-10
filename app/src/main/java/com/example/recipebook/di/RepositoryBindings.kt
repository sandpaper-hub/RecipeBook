package com.example.recipebook.di

import com.example.recipebook.data.repository.AuthenticationRepositoryImpl
import com.example.recipebook.data.repository.ProfileRepositoryImpl
import com.example.recipebook.data.repository.DataStoreRepositoryImpl
import com.example.recipebook.data.util.ImageCompressorImpl
import com.example.recipebook.domain.repository.ProfileRepository
import com.example.recipebook.domain.repository.AuthenticationRepository
import com.example.recipebook.domain.repository.DataStoreRepository
import com.example.recipebook.domain.util.ImageCompressor
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
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindFirestoreRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    @Binds
    abstract fun bindImageCompressor(
        imageCompressorImpl: ImageCompressorImpl
    ): ImageCompressor

    @Binds
    abstract fun bindSettingRepository(
        impl: DataStoreRepositoryImpl
    ): DataStoreRepository
}