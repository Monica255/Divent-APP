package com.example.divent.ui.content

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
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
    private val THEME_KEY = stringPreferencesKey("theme_key")
    private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_key")

    val theme: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: "Light"
        }

    val notificationsEnabled: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATIONS_KEY] ?: true
        }

    suspend fun saveTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = enabled
        }
    }
}
