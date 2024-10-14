package com.dicoding.myappp.models.retrofit

import com.dicoding.myappp.models.response.EventsResponse
import retrofit2.http.Query
import retrofit2.http.GET

interface ApiService {
    @GET("events")
    suspend fun searchEvents(@Query("active") active: Int, @Query("q") query: String): EventsResponse

    @GET("events")
    suspend fun getEvents(@Query("active") active: Int): EventsResponse
}