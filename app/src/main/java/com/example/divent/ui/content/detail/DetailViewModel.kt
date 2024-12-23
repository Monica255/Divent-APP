package com.example.divent.ui.content.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.divent.core.data.source.local.model.EntityEvent
import com.example.divent.core.domain.model.DetailEvent
import com.example.divent.core.domain.usecase.EventUseCase
import com.example.divent.core.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val eventUseCase: EventUseCase,
    private val roomUseCase: RoomUseCase
): ViewModel(){
    suspend fun getDetailEvent(id:Int) =eventUseCase.getDetailEvent(id).asLiveData()
    var detailData: DetailEvent?=null

    suspend fun isEventExist(id: Int): Boolean = roomUseCase.isEventExist(id)

    suspend  fun deleteEventById(id: Int) = roomUseCase.deleteEventById(id)

    suspend  fun insertEvent(event: EntityEvent) = roomUseCase.insertEvent(event)
}