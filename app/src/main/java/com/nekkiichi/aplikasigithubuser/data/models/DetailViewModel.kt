package com.nekkiichi.aplikasigithubuser.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.aplikasigithubuser.data.Result
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import com.nekkiichi.aplikasigithubuser.data.local.entity.UserEntity
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserDetail
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: UserRepository) : ViewModel() {

    private val _userFollowerFlow = MutableStateFlow<Result<List<UserItem>>>(Result.loading)
    val userFollowerFlow = _userFollowerFlow.asStateFlow()
    private val _userFollowingFlow = MutableStateFlow<Result<List<UserItem>>>(Result.loading)
    val userFollowingFlow = _userFollowingFlow.asStateFlow()

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite:LiveData<Boolean> = _isFavourite

    // retrieve user detail variabel from Parcelable into this view model
    fun retrieveUserDetail(data: UserDetail) {
        _userDetail.value = data
        getUserFollower(userDetail.value?.login.toString())
        getUserFollowing(userDetail.value?.login.toString())
        getFavouriteStatus(userDetail.value?.login.toString())
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
    fun toggleFavourite() {
        val isFavourite = _isFavourite.value
        viewModelScope.launch {
            if(isFavourite == true) {
                repository.deleteUserFromFavourite(UserEntity(userDetail.value?.login.toString(),userDetail.value?.avatarUrl.toString()))
            }else {
            repository.setUserAsFavourite(UserEntity(userDetail.value?.login.toString(),userDetail.value?.avatarUrl.toString()))
            }
            getFavouriteStatus(userDetail.value?.login.toString())
        }
    }
    fun getFavouriteStatus(login: String) {
        viewModelScope.launch {
            repository.isFavoriteUser(login).catch {
                _isFavourite.value = false
            }.collect {
                _isFavourite.value = it
            }
        }
    }
}