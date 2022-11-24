package com.nekkiichi.aplikasigithubuser.data.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import com.nekkiichi.aplikasigithubuser.data.Result
import com.nekkiichi.aplikasigithubuser.data.remote.response.UserItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableStateFlow<Result<List<UserItem>>>(Result.loading)
    val users = _users.asStateFlow()

    init {
        searchUserList(Q_DEFAULT)
    }
    fun searchUserList(q: String) {
       viewModelScope.launch {
           repository.searchUsers(q).collect {
               _users.value = it
           }
       }
    }
    companion object {
        val TAG = "MainViewModel"
        val Q_DEFAULT = "a"
    }
}