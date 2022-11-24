package com.nekkiichi.aplikasigithubuser.data.remote.services

import android.util.Log
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ApiWrapper {
    interface OnGetUserListener {
        fun onGetUserSuccess(item: UserDetail)
        fun onGetUserFailed(item: String)
    }
}