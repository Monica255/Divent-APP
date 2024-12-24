package com.example.divent.ui.content

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore(@ApplicationContext private val context: Context
)  {

    private val dataStore = context.dataStore
    private val THEMEKEY = stringPreferencesKey("theme_key")
    private val HOURKEY = intPreferencesKey("hour")
    private val MINUTEKEY = intPreferencesKey("minute")

    private val NOTIFICATIONSKEY = booleanPreferencesKey("notifications_key")

    val theme: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[THEMEKEY] ?: "Light"
        }

    val notificationsEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATIONSKEY] ?: true
        }

    val hour: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[HOURKEY] ?: 8
        }

    val minute: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[MINUTEKEY] ?: 0
        }
    suspend fun saveHour(hour: Int) {
        dataStore.edit { preferences ->
            preferences[HOURKEY] = hour
        }
    }
    suspend fun saveMinute(minute: Int) {
        dataStore.edit { preferences ->
            preferences[MINUTEKEY] = minute
        }
    }

    suspend fun saveTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEMEKEY] = theme
        }
    }

    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONSKEY] = enabled
        }
    }
}
