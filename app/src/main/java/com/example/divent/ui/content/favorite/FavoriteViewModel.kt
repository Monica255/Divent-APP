package com.example.divent.ui.content.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.divent.core.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel@Inject constructor(private val roomUseCase: RoomUseCase): ViewModel() {
    fun getEvents() = roomUseCase.getEvents().asLiveData()
}