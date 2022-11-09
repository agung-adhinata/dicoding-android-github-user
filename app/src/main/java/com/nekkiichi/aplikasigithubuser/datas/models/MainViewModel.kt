package com.nekkiichi.aplikasigithubuser.datas.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekkiichi.aplikasigithubuser.datas.ResponseUsers
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.services.ApiConfig
import com.nekkiichi.aplikasigithubuser.services.ApiWrapper
import okhttp3.internal.toImmutableList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        val TAG = "MainViewModel"
        val Q_DEFAULT = "a"
    }

    private val _userList = MutableLiveData<List<UserItem>>()
    val userList: LiveData<List<UserItem>> = _userList

    private val _userDetailList = MutableLiveData<List<UserDetail>>()
    val userDetailList: LiveData<List<UserDetail>> = _userDetailList

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getRawUsers(Q_DEFAULT)
    }
    private fun getRawUsers(q: String) {
        _isLoading.value = true
        ApiConfig.getApiService().getListUsers(q).enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(
                call: Call<ResponseUsers>,
                response: Response<ResponseUsers>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _userList.value = response.body()?.items
                } else {
                    Log.e(MainViewModel.TAG, "onResponse Failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                Log.e(MainViewModel.TAG, t.message.toString())
            }
        })

    }
}