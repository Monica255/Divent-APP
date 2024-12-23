package com.example.divent.ui.content.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.divent.core.domain.model.DetailEvent
import com.example.divent.core.domain.usecase.EventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val eventUseCase: EventUseCase): ViewModel(){
    suspend fun getDetailEvent(id:Int) =eventUseCase.getDetailEvent(id).asLiveData()
    var detailData: DetailEvent?=null
}