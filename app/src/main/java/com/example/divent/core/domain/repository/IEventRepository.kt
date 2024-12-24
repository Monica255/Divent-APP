package com.example.divent.core.domain.repository

import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.model.DetailEvent
import com.example.divent.util.EVENT
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
    suspend fun getEvent(type: EVENT, q:String?=null, limit:Int?=null): Flow<Resource<List<Event>>>
    suspend fun getDetailEvent(id:Int): Flow<Resource<DetailEvent>>
    suspend fun getEvent2(type: EVENT,  limit: Int = 0): Event?
}