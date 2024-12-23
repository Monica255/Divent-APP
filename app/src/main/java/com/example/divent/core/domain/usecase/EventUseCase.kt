package com.example.divent.core.domain.usecase

import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.data.source.remote.network.EVENT
import com.example.divent.core.domain.model.DetailEvent
import kotlinx.coroutines.flow.Flow

interface EventUseCase {

    suspend fun getEvent(type: EVENT, q:String?=null, limit:Int?=null): Flow<Resource<List<Event>>>
    suspend fun getDetailEvent(id:Int): Flow<Resource<DetailEvent>>
}