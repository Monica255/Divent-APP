package com.example.divent.core.domain.repository

import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.local.model.EntityEvent
import com.example.divent.core.data.source.remote.model.Event
import kotlinx.coroutines.flow.Flow

interface IRoomRepository {
    fun getEvents(): Flow<Resource<List<Event>>>

    suspend fun isEventExist(id: Int): Boolean

    suspend  fun deleteEventById(id: Int)

    suspend  fun insertEvent(event: EntityEvent)
}