package com.nekkiichi.aplikasigithubuser.services


import com.nekkiichi.aplikasigithubuser.datas.ResponseUsers
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
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
    ): Call<ResponseUsers>

    @Headers("Accept: application/vnd.github+json")
    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ):Call<UserDetail>


    @Headers("Accept: application/vnd.github+json")
    @GET("users/{username}/followers")
    fun getUserFollower(@Path("username") user: String): Call<List<UserItem>>

    @Headers("Accept: application/vnd.github+json")
    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") user: String): Call<List<UserItem>>
}