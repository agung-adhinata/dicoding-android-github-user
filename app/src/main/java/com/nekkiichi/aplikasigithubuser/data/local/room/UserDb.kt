package com.nekkiichi.aplikasigithubuser.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nekkiichi.aplikasigithubuser.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDb:RoomDatabase() {
    abstract fun userDao() : UserDao
}