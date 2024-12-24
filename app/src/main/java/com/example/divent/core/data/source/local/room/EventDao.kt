package com.example.divent.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.divent.core.data.source.local.model.EntityEvent

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(user: EntityEvent)

    @Query("SELECT * FROM Event")
    fun getAllEvents(): List<EntityEvent>

    @Query("SELECT EXISTS(SELECT 1 FROM Event WHERE id = :id)")
    suspend fun getEventById(id: Int): Boolean

    @Query("DELETE FROM Event WHERE id = :id")
    suspend fun deleteEvent(id: Int)
}