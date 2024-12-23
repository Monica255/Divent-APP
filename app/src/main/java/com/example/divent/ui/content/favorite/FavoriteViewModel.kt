package com.example.divent.ui.content.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.usecase.EventUseCase
import com.example.divent.core.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel@Inject constructor(private val roomUseCase: RoomUseCase): ViewModel() {
    var favEvets: List<Event>? = null
    fun getEvents() = roomUseCase.getEvents().asLiveData()
}