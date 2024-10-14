package com.dicoding.myappp.models.response

import com.google.gson.annotations.SerializedName

data class EventsResponse(

    @field:SerializedName("listEvents")
    val listEvents: List<ListEventsItem> = listOf(),

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,
)
