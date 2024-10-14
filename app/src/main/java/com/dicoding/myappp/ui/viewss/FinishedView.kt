package com.dicoding.myappp.ui.viewss

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.myappp.models.response.EventsResponse
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.models.retrofit.ApiConfig
import com.dicoding.myappp.models.retrofit.ApiService
import kotlinx.coroutines.*

class FinishedView : ViewModel() {
    private var apiConfig: ApiService = ApiConfig.getApiService()

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    private val _isLoadingf = MutableLiveData(false)
    val isLoadingf: LiveData<Boolean> = _isLoadingf

    fun getFinishedEvents() {
        viewModelScope.launch {
            _isLoadingf.value = true

            delay(0)

            try {
                val response: EventsResponse = apiConfig.getEvents(0)
                val events = response.listEvents
                _finishedEvents.postValue(events)
            } catch (e: Exception) {
                Log.e("FinishedViewModel", "Error get finished events", e)
            } finally {
                _isLoadingf.value = false
            }
        }
    }

    fun searchFinishedEvents(query: String) {
        viewModelScope.launch {
            _isLoadingf.value = true

            delay(1000)

            try {
                val response: EventsResponse = apiConfig.searchEvents(ApiConfig.FINISHED_EVENT, query)
                val events = response.listEvents
                _finishedEvents.postValue(events)
            } catch (e: Exception) {
                Log.e("FinishedViewModel", "Error search finished events", e)
            } finally {
                _isLoadingf.value = false
            }
        }
    }
}
