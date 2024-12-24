package com.example.divent.core.data.source.local.room

import com.example.divent.core.data.source.local.model.EntityEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val eventDao: EventDao
) {
    fun getEvents(): List<EntityEvent> = eventDao.getAllEvents()

    suspend fun isEventExist(id: Int): Boolean {
        return eventDao.getEventById(id)
    }

    suspend  fun deleteEventById(id: Int) {
        eventDao.deleteEvent(id)
    }

    suspend  fun insertEvent(event: EntityEvent) {
        eventDao.insertEvent(event)
    }
}