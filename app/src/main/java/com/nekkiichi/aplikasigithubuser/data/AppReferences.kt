package com.nekkiichi.aplikasigithubuser.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppReferences constructor(private val  dataStore: DataStore<Preferences>){
    fun getTheme():Flow<Boolean> {
        return dataStore.data.map {
            it[THEME_KEY] ?: false
        }
    }
    suspend fun saveThemeData(darkModeStatus: Boolean) {
        dataStore.edit {
            it[THEME_KEY]= darkModeStatus
        }
    }
    companion object {
        val THEME_KEY = booleanPreferencesKey("theme_setting")
    }

}