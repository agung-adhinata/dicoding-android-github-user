package com.nekkiichi.aplikasigithubuser.services

import android.util.Log
import com.nekkiichi.aplikasigithubuser.datas.ResponseUsers
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.datas.models.MainViewModel
import kotlinx.coroutines.awaitAll
import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiWrapper {
    interface OnGetUserListener {
        fun onGetUserSuccess(item: UserDetail)
        fun onGetUserFailed(item: String)
    }
    fun getUserDetail(username: String, listener:OnGetUserListener): UserDetail {
        var myUser = UserDetail()
        ApiConfig.getApiService().getUser(username).enqueue(
            object : Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            Log.d("TES", "done")
                            listener.onGetUserSuccess(body)
                        }else {
                            listener.onGetUserFailed(response.message())
                        }
                    }else {
                        listener.onGetUserFailed(response.message())
                    }

                }
                override fun onFailure(call: Call<UserDetail>, t: Throwable) {

                    t.message?.let { Log.e("API", it) }

                }
            }
        )
        return myUser
    }

}