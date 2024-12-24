package com.example.divent.util

import com.example.divent.core.data.source.local.model.EntityEvent
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.model.DetailEvent

object Mapper {
    fun entityToDomain(entity:EntityEvent):Event{
        return Event(
            id = entity.id,
            beginTime = entity.beginTime,
            imageLogo = entity.imageLogo,
            mediaCover = entity.mediaCover,
            name = entity.name,
            quota = entity.quota,
            registrants = entity.registrants
        )
    }

    fun detailDomainToEntity(domain:DetailEvent):EntityEvent{
        return EntityEvent(
            id = domain.id,
            beginTime = domain.beginTime,
            imageLogo = domain.imageLogo,
            mediaCover = domain.mediaCover,
            name = domain.name,
            quota = domain.quota,
            registrants = domain.registrants
        )
    }
}