package com.example.divent.core.data.source.remote.network

import android.util.Log
import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.model.Event
import com.example.divent.core.domain.model.DetailEvent
import com.example.divent.util.EVENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
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

    suspend fun getEvent2(type: EVENT, limit: Int = 1): Event? {
        return withContext(Dispatchers.IO) {
            try {
                val response = defaultApiService.getOneEvent(type.value, limit)
                if (response.isSuccessful) {
                    val success = response.body()
                    success?.let {
                        if (!it.error) {
                            return@withContext it.listEvents[0]
                        }
                    }
                } else {
                    Log.d("dailyworker", "Not successful: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.d("dailyworker", "Exception: ${e.message}")
            }
            return@withContext null
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