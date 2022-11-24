package com.nekkiichi.aplikasigithubuser.data.remote.services

import android.util.Log
import androidx.core.graphics.createBitmap
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class ApiWrapper {
    interface OnGetUserListener {
        fun onGetUserSuccess(item: UserDetail)
        fun onGetUserFailed(item: String)
    }
    fun getUserDetails(username: String, listener: OnGetUserListener) {
        Log.d(TAG, "get data")
        ApiConfig.getApiService().getUserFull(username).enqueue(object : Callback<UserDetail>{
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                if(response.isSuccessful) {
                    val body = response.body()
                    Log.d(TAG, "success retrieve data")
                    if(body!= null) {
                        listener.onGetUserSuccess(body)
                    }else {
                        listener.onGetUserFailed(response.message())
                        Log.d(TAG, "missing body, ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                listener.onGetUserFailed(t.toString())
            }

        })
    }
    companion object {
        val TAG = "ApiWrapper"
    }
}
