package com.nekkiichi.aplikasigithubuser.data.remote.services


import com.nekkiichi.aplikasigithubuser.data.remote.response.ResponseUsers
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val token = "Authorization: Bearer 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"

interface ApiService {
    @Headers("Accept: application/vnd.github+json", token)
    @GET("search/users")
    suspend fun getListUsers(
        @Query("q") q: String,
        @Query("per_page") perPage: Int = 10
    ): ResponseUsers

    @Headers("Accept: application/vnd.github+json", token)
    @GET("users/{username}")
    fun getUserFull(
        @Path("username") username: String
    ):Call<UserDetail>

    @Headers("Accept: application/vnd.github+json",token)
    @GET("users/{username}/followers")
    suspend fun getUserFollower(@Path("username") user: String): List<UserItem>

    @Headers("Accept: application/vnd.github+json", token)
    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") user: String): List<UserItem>
}