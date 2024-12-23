package com.example.divent.core.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Event")
data class EntityEvent(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageLogo: String,
    val mediaCover: String,
    val quota: Int,
    val registrants: Int,
    val beginTime: String,
)