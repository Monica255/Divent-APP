package com.example.divent

import com.example.divent.core.data.source.repository.EventRepository
import com.example.divent.core.data.source.repository.RoomRepository
import com.example.divent.core.domain.usecase.EventInteractor
import com.example.divent.core.domain.usecase.EventUseCase
import com.example.divent.core.domain.usecase.RoomInteractor
import com.example.divent.core.domain.usecase.RoomUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEventUseCase(eventRepository: EventRepository): EventUseCase=EventInteractor(eventRepository)

    @Provides
    @Singleton
    fun provideRoomUseCase(roomRepository: RoomRepository): RoomUseCase=RoomInteractor(roomRepository)


}