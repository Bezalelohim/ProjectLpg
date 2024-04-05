package com.example.projectlpg.data.di

import android.content.Context
import com.example.projectlpg.data.repository.AppRepository
import com.example.projectlpg.network.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides

    fun provideNetworkManager(
        @ApplicationContext context: Context,
        appRepository: AppRepository
    ): NetworkManager {
        return NetworkManager(context, appRepository)
    }
}