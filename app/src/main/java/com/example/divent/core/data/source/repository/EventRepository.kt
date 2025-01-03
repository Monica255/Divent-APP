package com.example.divent.core.data.source.repository

import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.data.source.remote.network.RemoteDataSource
import com.example.divent.core.domain.repository.IEventRepository
import com.example.divent.util.EVENT
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
):IEventRepository {

    override suspend fun getEvent(
        type: EVENT,
        q: String?,
        limit: Int?
    ) =remoteDataSource.getEvent(type,q,limit)

    override suspend fun getDetailEvent(id: Int) = remoteDataSource.getDetailEvent(id)
    override suspend fun getEvent2(type: EVENT, limit: Int): Event? =remoteDataSource.getEvent2(type,limit)


}