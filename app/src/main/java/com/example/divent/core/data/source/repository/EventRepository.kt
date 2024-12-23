package com.example.divent.core.data.source.repository

import com.example.divent.core.data.source.remote.network.EVENT
import com.example.divent.core.data.source.remote.network.RemoteDataSource
import com.example.divent.core.domain.repository.IEventRepository
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

}