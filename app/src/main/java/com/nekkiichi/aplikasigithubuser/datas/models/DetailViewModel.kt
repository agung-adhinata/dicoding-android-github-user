package com.nekkiichi.aplikasigithubuser.datas.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    companion object {
        val TAG = "DetailViewOwner"
    }
    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _userFollower = MutableLiveData<List<UserItem>>()
    val userFollower: LiveData<List<UserItem>> = _userFollower
    private val _followerErrMsg = MutableLiveData<String>()
    val followerErrMsg : LiveData<String> = _followerErrMsg

    private val _userFollowing = MutableLiveData<List<UserItem>>()
    val userFollowing: LiveData<List<UserItem>> = _userFollowing
    private val _followingErrMsg = MutableLiveData<String>()
    val followingErrMsg : LiveData<String> = _followingErrMsg

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading


    // retrieve user detail variabel from Parcelable into this view model
    fun retrieveUserDetail(data: UserDetail) {
        _userDetail.value = data
        getUserFollower(data.login.toString())
        getUserFollowing(data.login.toString())
    }
    fun getUserDetail(username: String) {
        ApiConfig.getApiService().getUser(username).enqueue(
            object : Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userDetail.value = response.body()
                    }
                }
                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.e("API", it) }

                }
            }
        )
    }
    fun getUserFollower(username: String) {
        ApiConfig.getApiService().getUserFollower(username).enqueue(
            object : Callback<List<UserItem>>{
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
                    if(response.isSuccessful) {
                        _userFollower.value = response.body()
                    }else {
                        _followerErrMsg.value = response.message()
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            }
        )
    }
    fun getUserFollowing(username: String) {
        ApiConfig.getApiService().getUserFollowing(username).enqueue(
            object : Callback<List<UserItem>>{
                override fun onResponse(
                    call: Call<List<UserItem>>,
                    response: Response<List<UserItem>>
                ) {
                    if(response.isSuccessful) {
                        _userFollowing.value = response.body()
                    }else {
                        _followingErrMsg.value = response.message()
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            }
        )
    }
}