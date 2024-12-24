package com.example.divent

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.divent.core.notification.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application(){
    init {
        instance = this
    }
    @Inject
    lateinit var myWorkerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(myWorkerFactory)
                .build()
        )
    }

    companion object {
        private var instance: MyApplication? = null

        @Suppress("unused")
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}