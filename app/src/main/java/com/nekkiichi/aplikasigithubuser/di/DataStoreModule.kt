package com.nekkiichi.aplikasigithubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nekkiichi.aplikasigithubuser.data.AppReferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context):DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideAppReferences(dataStore: DataStore<Preferences>):AppReferences = AppReferences(dataStore)
}