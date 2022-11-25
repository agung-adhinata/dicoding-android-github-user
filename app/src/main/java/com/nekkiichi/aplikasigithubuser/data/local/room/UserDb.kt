package com.nekkiichi.aplikasigithubuser.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nekkiichi.aplikasigithubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 3, exportSchema = true)
abstract class UserDb:RoomDatabase() {
    abstract fun userDao() : UserDao
}