package com.nekkiichi.aplikasigithubuser.data.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.aplikasigithubuser.data.Result
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import com.nekkiichi.aplikasigithubuser.data.local.entity.UserEntity
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _favourites = MutableLiveData<List<UserEntity>>()
    val favourites:LiveData<List<UserEntity>> = _favourites
    private val _favouritesList = MutableStateFlow<Result<List<UserItem>>>(Result.loading)
    val favouritesList = _favouritesList.asStateFlow()
    init {
        getFavouriteUsers()
    }

    fun getFavouriteUsers() {
        Log.d(TAG, "getting data from db")
        viewModelScope.launch {
            userRepository.getAllSavedUser().collect{
                _favourites.value = it
            }
        }
    }
    fun getUserLists() {
        viewModelScope.launch{
            userRepository.convertUserEntityToUserItem(_favourites.value!!).collect {
                _favouritesList.value = it
            }
        }
    }

    companion object {
        val TAG = "FavouriteViewModel"
    }

}