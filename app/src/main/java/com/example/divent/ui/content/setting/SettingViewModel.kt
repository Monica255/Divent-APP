package com.example.divent.ui.content.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.divent.ui.content.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    private val _theme = MutableLiveData<String?>()
    val theme: LiveData<String?> get() = _theme

    private val _notificationEnabled = MutableLiveData(false)
    val notificationEnabled: LiveData<Boolean> get() = _notificationEnabled

    private val _time = MutableLiveData<List<Int>>()
    val time: LiveData<List<Int>> get() = _time

    init {
        viewModelScope.launch {
            settingsDataStore.theme.collect { newTheme ->
                _theme.postValue(newTheme)
            }
        }
        viewModelScope.launch {
            settingsDataStore.notificationsEnabled.collect { isEnabled ->
                _notificationEnabled.postValue(isEnabled)
            }
        }
        viewModelScope.launch {
            settingsDataStore.hour.combine(settingsDataStore.minute) { hour, minute ->
                listOf(hour, minute)
            }.collect { combinedTime ->
                _time.postValue(combinedTime)
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
    fun setHour(hour: Int) {
        viewModelScope.launch {
            settingsDataStore.saveHour(hour)
        }
    }
    fun setMinute(minute: Int) {
        viewModelScope.launch {
            settingsDataStore.saveMinute(minute)
        }
    }
}
