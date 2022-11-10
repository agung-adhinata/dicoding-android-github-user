package com.nekkiichi.aplikasigithubuser.datas.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _userDetails = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetails
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading
    init {
        getUserDetail("q")
    }
    fun getUserDetail(username: String) {
        ApiConfig.getApiService().getUser(username).enqueue(
            object : Callback<UserDetail> {
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userDetails.value = response.body()
                    }
                }
                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.e("API", it) }

                }
            }
        )
    }
}