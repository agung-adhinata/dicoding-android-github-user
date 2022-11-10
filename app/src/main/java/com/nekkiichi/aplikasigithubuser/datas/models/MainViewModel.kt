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
        val TAG = "MainViewModel"
        val Q_DEFAULT = "a"
    }

    private val _userList = MutableLiveData<List<UserItem>>()
    val userList: LiveData<List<UserItem>> = _userList
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading
    private val _errorMsg = MutableLiveData<String>()
    val errorMsg :LiveData<String> = _errorMsg

    init {
        searchUserList(Q_DEFAULT)
    }
    fun searchUserList(q: String) {
        _isLoading.value = true
        _userList.value = listOf()
        ApiConfig.getApiService().getListUsers(q).enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(
                call: Call<ResponseUsers>,
                response: Response<ResponseUsers>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userList.value = response.body()?.items
                } else {
                    _errorMsg.value = response.message()
                    Log.e(TAG, "onResponse Failed: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                _isLoading.value = false
                _errorMsg.value = t.message.toString()
                Log.e(TAG, t.message.toString())
            }
        })

    }
}