package com.nekkiichi.aplikasigithubuser.data.remote.services

import android.util.Log
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiWrapper {
    interface OnGetUserListener {
        fun onGetUserSuccess(item: UserDetail)
        fun onGetUserFailed(item: String)
    }
    fun getUserDetail(username: String, listener: OnGetUserListener): UserDetail {
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