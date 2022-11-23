package com.nekkiichi.aplikasigithubuser.data

import android.util.Log
import com.nekkiichi.aplikasigithubuser.data.local.entity.UserEntity
import com.nekkiichi.aplikasigithubuser.data.local.room.UserDao
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val apiService: ApiService,
    private val userDao: UserDao,

) {
    fun searchUsers(username: String): Flow<com.nekkiichi.aplikasigithubuser.data.Result<List<UserItem>>> = flow {
        emit(Result.loading)
        try {
            val res = apiService.getListUsers(username).items
            emit(Result.Success(res))
        }catch (e:Exception) {
            Log.d(TAG, "error when retrieve searchUsers: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }
    fun getUserDetail(username: String): Flow<Result<UserDetail>> = flow {
        try {
            val res = apiService.getUser(username)
            emit(Result.Success(res))
        }catch (e: Exception) {
            Log.d(TAG, "error when run GetUser: ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowerOrFollowing(username: String, isFollower: Boolean) :Flow<Result<List<UserItem>>> = flow {
        emit(Result.loading)
        if (isFollower) {
            try {
                val res = apiService.getUserFollower(username)
                emit(Result.Success(res))
            }catch (e: Exception) {
                Log.d(TAG, "error when run getFollowerOrFollowing: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }else{
            try {
                val res = apiService.getUserFollowing(username)
                emit(Result.Success(res))
            }catch (e: Exception) {
                Log.d(TAG, "error when run getFollowerOrFollowing: ${e.message.toString()}")
                emit(Result.Error(e.message.toString()))
            }
        }
    }
    fun isFavoriteUser(id: String): Flow<Boolean> = userDao.isFavoriteUser(id)
    fun getAllSavedUser(): Flow<List<UserEntity>> = userDao.getAllUser();
    suspend fun deleteUserFromFavourite(user: UserEntity) {
        userDao.delete(user)
    }
    suspend fun setUserAsFavourite(user: UserEntity) {
        userDao.insert(user)
    }

    companion object {
        private const val API_KEY = "Bearer"
        private const val TAG = "UserRepository"
    }
}