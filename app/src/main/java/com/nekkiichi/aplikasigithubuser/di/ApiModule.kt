package com.nekkiichi.aplikasigithubuser.di

import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiConfig
import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideApiService(): ApiService = ApiConfig.getApiService()
}