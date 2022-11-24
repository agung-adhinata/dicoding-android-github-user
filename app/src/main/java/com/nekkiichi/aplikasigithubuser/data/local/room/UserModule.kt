package com.nekkiichi.aplikasigithubuser.data.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    fun provideUserDao(userDb: UserDb): UserDao {
        return userDb.userDao()
    }
    @Provides
    fun provideUserDatabase(@ApplicationContext appContext: Context):UserDb {
        return Room.databaseBuilder(appContext,UserDb::class.java, "userFavourite").build()
    }
}