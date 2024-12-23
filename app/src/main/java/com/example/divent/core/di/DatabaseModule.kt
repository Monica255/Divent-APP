package com.example.divent.core.di

import android.content.Context
import androidx.room.Room
import com.example.divent.core.data.source.local.room.EventDao
import com.example.divent.core.data.source.local.room.EventDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventDatabase {
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "event_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideEventDao(database: EventDatabase): EventDao {
        return database.eventDao()
    }
}