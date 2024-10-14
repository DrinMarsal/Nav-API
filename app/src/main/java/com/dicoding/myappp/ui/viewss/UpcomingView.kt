package com.dicoding.myappp.ui.viewss

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.dicoding.myappp.models.response.EventsResponse
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.models.retrofit.ApiService
import com.dicoding.myappp.models.retrofit.ApiConfig
import kotlinx.coroutines.*
import android.util.Log

class UpcomingView : ViewModel() {
    private var apiConfig: ApiService = ApiConfig.getApiService()
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUpcomingEvents() {
        viewModelScope.launch {
            _isLoading.value = true

            delay(0)

            try {
                val response: EventsResponse = apiConfig.getEvents(1)
                val events = response.listEvents
                _upcomingEvents.postValue(events)
            } catch (e: Exception) {
                Log.e("UpcomingView", "Error get upcoming events", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchUpcomingEvents(query: String) {
        viewModelScope.launch {
            _isLoading.value = true

            delay(1000)

            try {
                val response: EventsResponse = apiConfig.searchEvents(ApiConfig.UPCOMING_EVENT, query)
                val events = response.listEvents
                _upcomingEvents.postValue(events)
            } catch (e: Exception) {
                Log.e("UpcomingViewModel", "Error search upcoming events", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
