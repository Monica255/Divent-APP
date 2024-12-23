package com.example.divent.ui.content.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.usecase.EventUseCase
import com.example.divent.util.EVENT
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val eventUseCase: EventUseCase): ViewModel(){
    suspend fun getEvent(type: EVENT, q:String?=null, limit:Int=30)= eventUseCase.getEvent(type,q,limit).asLiveData()
    var upcomingEvent: List<Event>? = null
    var finishedEvent: List<Event>? = null
}