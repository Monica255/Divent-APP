package com.example.divent.core.domain.usecase

import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.local.model.EntityEvent
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.repository.IRoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomInteractor @Inject constructor(private val repo: IRoomRepository):RoomUseCase {
    override fun getEvents(): Flow<Resource<List<Event>>> = repo.getEvents()

    override suspend fun isEventExist(id: Int): Boolean = repo.isEventExist(id)

    override suspend fun deleteEventById(id: Int) {
        repo.deleteEventById(id)
    }

    override suspend fun insertEvent(event: EntityEvent) {
        repo.insertEvent(event)
    }
}