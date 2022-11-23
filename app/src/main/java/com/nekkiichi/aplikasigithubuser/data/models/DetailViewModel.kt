package com.nekkiichi.aplikasigithubuser.data.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.remote.services.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class DetailViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {
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

    // retrieve user detail variabel from Parcelable into this view model
    fun retrieveUserDetail(data: UserDetail) {
        _userDetail.value = data
        getUserFollower(data.login.toString())
        getUserFollowing(data.login.toString())
    }
    fun getUserFollower(username: String) {
        viewModelScope.launch {
            repository.getFollowerOrFollowing(username,true).collect {

            }
        }
    }
    fun getUserFollowing(username: String) {

    }
}