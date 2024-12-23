package com.example.divent.ui.content.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divent.ui.content.SettingsDataStore
import com.example.divent.util.SettingUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _theme = MutableLiveData<String?>()
    val theme: LiveData<String?> get() = _theme

    private val _notificationEnabled = MutableLiveData<Boolean>()
    val notificationEnabled: LiveData<Boolean> get() = _notificationEnabled

    init {
        viewModelScope.launch {
            settingsDataStore.theme.collect { newTheme ->
                _theme.postValue(newTheme)
            }
            settingsDataStore.notificationsEnabled.collect { isEnabled ->
                _notificationEnabled.postValue(isEnabled)
            }
        }
    }

    fun setTheme(theme: String) {
        viewModelScope.launch {
            settingsDataStore.saveTheme(theme)
        }
    }

    fun setNotificationEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.saveNotificationsEnabled(isEnabled)
        }
    }
}
