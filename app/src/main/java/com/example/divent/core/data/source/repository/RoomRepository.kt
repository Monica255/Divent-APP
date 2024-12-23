package com.example.divent.core.data.source.repository

import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.local.model.EntityEvent
import com.example.divent.core.data.source.local.room.LocalDataSource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.repository.IRoomRepository
import com.example.divent.util.Mapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(
    private val localDataSource: LocalDataSource
): IRoomRepository {

    override fun getEvents(): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        try {
            val entityEvents = localDataSource.getEvents()
            val events = entityEvents.map { entityEvent ->
                Mapper.entityToDomain(entityEvent)
            }
            emit(Resource.Success(events))
        } catch (exception: Exception) {
            emit(Resource.Error(exception.message ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun isEventExist(id: Int): Boolean = localDataSource.isEventExist(id)

    override suspend fun deleteEventById(id: Int) {
        localDataSource.deleteEventById(id)
    }

    override suspend fun insertEvent(event: EntityEvent) {
        localDataSource.insertEvent(event)
    }
}