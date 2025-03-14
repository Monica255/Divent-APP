package com.example.divent.util

import androidx.appcompat.app.AppCompatDelegate

object SettingUtil {
    fun setDarkMode(isDark:Boolean){
        if(isDark){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}