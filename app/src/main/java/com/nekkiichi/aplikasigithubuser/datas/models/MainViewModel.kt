package com.nekkiichi.aplikasigithubuser.datas.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekkiichi.aplikasigithubuser.datas.ResponseUsers
import com.nekkiichi.aplikasigithubuser.datas.UserDetail
import com.nekkiichi.aplikasigithubuser.datas.UserItem
import com.nekkiichi.aplikasigithubuser.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        val USERNAME = "username"
        val TAG = "MainViewModel"
        val Q_DEFAULT = "a"
    }

    private val _userList = MutableLiveData<List<UserItem>>()
    val userList: LiveData<List<UserItem>> = _userList

    private val _userDetailList = MutableLiveData<MutableList<UserDetail>>()
    val userDetailList: LiveData<MutableList<UserDetail>> = _userDetailList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUsers(Q_DEFAULT)
        getDetailUsers()
    }

    private fun getUsers(q: String) {
        _isLoading.value = true
        val client =
            ApiConfig.getApiService().getListUsers(q).enqueue(object : Callback<ResponseUsers> {

                override fun onResponse(
                    call: Call<ResponseUsers>,
                    response: Response<ResponseUsers>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userList.value = response.body()?.items
                    } else {
                        Log.e(TAG, "onResponse Failed: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    fun getDetailUsers() {
        _isLoading.value = true
        _userDetailList.value = mutableListOf()
        _userList.value?.forEach {
            ApiConfig.getApiService().getUser(it.login.toString())
                .enqueue(object : Callback<UserDetail> {
                    override fun onResponse(
                        call: Call<UserDetail>,
                        response: Response<UserDetail>
                    ) {
                        if (response.isSuccessful) {
                            _userDetailList.value?.add(response.body()!!)
                        } else {
                            Log.e(TAG, "onResponse Failure ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                        Log.e(TAG, "onFailure ${t.message}")
                    }
                })
        }
        _isLoading.value = false
    }
}