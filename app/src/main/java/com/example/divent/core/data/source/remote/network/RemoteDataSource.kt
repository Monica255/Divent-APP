package com.example.divent.core.data.source.remote.network

import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.model.DetailEvent
import com.example.divent.util.EVENT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(
    private val defaultApiService: ApiService,
) {
    suspend fun getEvent(type: EVENT, q: String? = null, limit: Int? = null): Flow<Resource<List<Event>>> = flow {
        emit(Resource.Loading())
        try {
            val response = defaultApiService.getEvent(type.value, q, limit)

            if (response.isSuccessful) {
                val success = response.body()
                success?.let {
                    if (!it.error) {
                        emit(Resource.Success(it.listEvents))
                    } else {
                        emit(Resource.Error("Unsuccessful response: ${response.code()}"))
                    }
                }
            } else {
                emit(Resource.Error("Unsuccessful response: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error processing response: ${e.message}"))
        }
    }

    suspend fun getDetailEvent(id:Int): Flow<Resource<DetailEvent>> = flow {
        emit(Resource.Loading())
        try {
            val response = defaultApiService.getDetailEvent(id)

            if (response.isSuccessful) {
                val success = response.body()
                success?.let {
                    if (!it.error) {
                        emit(Resource.Success(it.event))
                    } else {
                        emit(Resource.Error("Unsuccessful response: ${response.code()}"))
                    }
                }
            } else {
                emit(Resource.Error("Unsuccessful response: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Error processing response: ${e.message}"))
        }
    }

}