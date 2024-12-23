package com.example.divent

import android.app.Application
import android.content.Context
import androidx.activity.viewModels
import com.example.divent.ui.content.SettingsDataStore
import com.example.divent.ui.content.setting.SettingsViewModel
import com.example.divent.util.SettingUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        @Suppress("unused")
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}