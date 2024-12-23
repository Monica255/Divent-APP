package com.example.divent

import com.example.divent.core.data.source.repository.EventRepository
import com.example.divent.core.domain.usecase.EventInteractor
import com.example.divent.core.domain.usecase.EventUseCase
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


}