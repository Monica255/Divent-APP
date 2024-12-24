package com.example.divent.core.notification

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): Worker
}

class HiltWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out Worker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        val workerClass = Class.forName(workerClassName).asSubclass(Worker::class.java)
        val factory = workerFactories[workerClass]?.get()
            ?: throw IllegalArgumentException("Unknown worker class: $workerClassName")
        return factory.create(appContext, workerParameters)
    }
}