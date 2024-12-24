package com.example.divent.core.di

import androidx.work.Worker
import com.example.divent.core.notification.ChildWorkerFactory
import com.example.divent.core.notification.DailyReminderWorker
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out Worker>)

@Module
@InstallIn(SingletonComponent::class)
@Suppress("unused")
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(DailyReminderWorker::class)
    internal abstract fun bindMyWorkerFactory(worker: DailyReminderWorker.Factory): ChildWorkerFactory
}