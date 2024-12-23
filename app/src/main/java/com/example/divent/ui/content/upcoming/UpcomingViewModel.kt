package com.example.divent.ui.content.upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.data.source.remote.network.EVENT
import com.example.divent.core.domain.usecase.EventUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UpcomingViewModel @Inject constructor(private val eventUseCase: EventUseCase): ViewModel(){
    suspend fun getEvent(type: EVENT, q:String?=null, limit:Int=30)= eventUseCase.getEvent(type,q,limit).asLiveData()

    val showErrorLiveData = MutableLiveData<Boolean>()
    val showLoadingLiveData = MutableLiveData<Boolean>()

    val moreUpcomingEventLiveData = MutableLiveData<List<Event>?>()

    fun searchEvents(query: String?= null) {
        viewModelScope.launch {
            val result = getEvent(type = EVENT.UPCOMING, q = query)
            result.observeForever { resource ->
                when (resource) {
                    is Resource.Success -> {
                        moreUpcomingEventLiveData.postValue(resource.data)
                        showErrorLiveData.postValue(false)
                        showLoadingLiveData.postValue(false)
                    }
                    is Resource.Error -> {
                        if (moreUpcomingEventLiveData.value.isNullOrEmpty()) {
                            showErrorLiveData.postValue(true)
                        }
                        showLoadingLiveData.postValue(false)
                    }
                    is Resource.Loading -> {
                        if (moreUpcomingEventLiveData.value.isNullOrEmpty()) {
                            showLoadingLiveData.postValue(true)
                            showErrorLiveData.postValue(false)
                        }
                    }
                }
            }
        }
    }
}