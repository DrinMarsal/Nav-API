package com.dicoding.myappp.ui.viewss

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.dicoding.myappp.models.response.EventsResponse
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.models.retrofit.ApiService
import com.dicoding.myappp.models.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log

class HomeView : ViewModel() {

    private var apiConfig: ApiService = ApiConfig.getApiService()

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents
    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    fun getUpcomingEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: EventsResponse = apiConfig.getEvents(1)
                val events = response.listEvents
                _upcomingEvents.postValue(events)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error get upcoming events", e)
            }
        }
    }

    fun getFinishedEvents() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: EventsResponse = apiConfig.getEvents(0)
                val events = response.listEvents
                _finishedEvents.postValue(events)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Error get finished events", e)
            }
        }
    }
}
