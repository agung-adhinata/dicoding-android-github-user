package com.nekkiichi.aplikasigithubuser.data.remote.services


import com.nekkiichi.aplikasigithubuser.data.remote.response.ResponseUsers
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @Headers("Accept: application/vnd.github+json")
    @GET("search/users")
    fun getListUsers(
        @Query("q") q: String,
        @Query("per_page") perPage: Int = 10
    ): ResponseUsers
    @Headers("Accept: application/vnd.github+json")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ):UserDetail


    @Headers("Accept: application/vnd.github+json")
    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") user: String): List<UserItem>

    @Headers("Accept: application/vnd.github+json")
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") user: String): List<UserItem>
}