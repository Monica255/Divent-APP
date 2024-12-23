package com.example.divent.core.domain.usecase

import com.example.divent.core.data.source.remote.network.EVENT
import com.example.divent.core.domain.repository.IEventRepository
import javax.inject.Inject

class EventInteractor @Inject constructor(private val repo:IEventRepository):EventUseCase {
    override suspend fun getEvent(
        type: EVENT,
        q: String?,
        limit: Int?
    ) = repo.getEvent(type,q ,limit)

    override suspend fun getDetailEvent(id: Int) = repo.getDetailEvent(id)
}