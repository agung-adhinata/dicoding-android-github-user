package com.nekkiichi.aplikasigithubuser.data.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nekkiichi.aplikasigithubuser.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferencesViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val getThemeSettings: Flow<Boolean> = userRepository.getThemeSetting()

    fun saveThemeSetting(darkModeStatus: Boolean) {
        viewModelScope.launch {
            userRepository.setTheme(darkModeStatus)
        }
    }
}