package com.nekkiichi.aplikasigithubuser.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import com.nekkiichi.aplikasigithubuser.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {
    companion object {
        val TAG = "DetailViewOwner"
    }
    private val _userFollowerFlow = MutableStateFlow<Result<List<UserItem>>>(Result.loading);
    val userFollowerFlow = _userFollowerFlow.asStateFlow()
    private val _userFollowingFlow = MutableStateFlow<Result<List<UserItem>>>(Result.loading);
    val userFollowingFlow = _userFollowingFlow.asStateFlow()

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    // retrieve user detail variabel from Parcelable into this view model
    fun retrieveUserDetail(data: UserDetail) {
        _userDetail.value = data
        getUserFollower(data.login.toString())
        getUserFollowing(data.login.toString())
    }
    fun getUserFollower(username: String) {
        viewModelScope.launch {
            repository.getFollowerOrFollowing(username,true).collect {
                _userFollowerFlow.value = it
            }
        }
    }
    fun getUserFollowing(username: String) {
        viewModelScope.launch {
            repository.getFollowerOrFollowing(username, false).collect {
                _userFollowingFlow.value = it
            }
        }
    }
}