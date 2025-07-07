package com.mdrapp.de.di

import com.mdrapp.de.data.storage.SessionStorageImpl
import com.mdrapp.de.domain.storage.SessionStorage
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface StorageModule {

    @Binds
    @Singleton
    fun provideSessionStorage(storage: SessionStorageImpl): SessionStorage
}