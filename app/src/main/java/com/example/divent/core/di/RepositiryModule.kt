package com.example.divent.core.di

import com.example.divent.core.data.source.repository.EventRepository
import com.example.divent.core.data.source.repository.RoomRepository
import com.example.divent.core.domain.repository.IEventRepository
import com.example.divent.core.domain.repository.IRoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideEventRepository(eventRepository: EventRepository): IEventRepository
    @Binds
    abstract fun provideRoomRepository(roomRepository: RoomRepository): IRoomRepository

}