package com.nekkiichi.aplikasigithubuser.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nekkiichi.aplikasigithubuser.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userEntity: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query ("SELECT EXISTS(SELECT * FROM user WHERE login = :login)")
    fun isFavoriteUser(login: String): Flow<Boolean>
}