package com.example.divent.core.data.source.remote.network

import com.example.divent.core.data.source.remote.model.DetailEventResponse
import com.example.divent.core.data.source.remote.model.EventResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("events")
    suspend fun getEvent(
        @Query("active") active: Int,
        @Query("q") q: String? = null,
        @Query("limit") limit: Int? =null,
    ): Response<EventResponse>

    @GET("events/{id}")
    suspend fun getDetailEvent(
        @Path("id") id: Int,
    ): Response<DetailEventResponse>



}