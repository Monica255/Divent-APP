package com.example.divent.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.divent.core.data.source.local.model.EntityEvent

@Database(entities = [EntityEvent::class], version = 2, exportSchema = false)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}