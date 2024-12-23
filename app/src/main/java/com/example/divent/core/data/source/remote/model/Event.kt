package com.example.divent.core.data.source.remote.model

import com.example.divent.core.domain.model.DetailEvent

data class EventResponse(
    val error: Boolean,
    val message: String,
    val listEvents: List<Event>
)

data class DetailEventResponse(
    val error: Boolean,
    val message: String,
    val event: DetailEvent
)

data class Event(
    val id: Int,
    val name: String,
    val imageLogo: String,
    val mediaCover: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
)
