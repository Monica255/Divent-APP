package com.example.divent

import android.app.Application
import android.content.Context
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