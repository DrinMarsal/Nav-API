package com.dicoding.myappp.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.dicoding.myappp.R
import com.dicoding.myappp.models.layoutmanager.NonScrollableLinearLayoutManager
import com.dicoding.myappp.models.event.EventAdapterU
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.models.event.EventAdapterF
import com.dicoding.myappp.ui.viewss.HomeView
import com.dicoding.myappp.ui.DetailActivity
import android.os.Handler
import android.os.Bundle
import android.os.Looper

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeView
    private lateinit var upcomingEventAdapter: EventAdapterU
    private lateinit var finishedEventAdapter: EventAdapterF
    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var finishedRecyclerView: RecyclerView
    private lateinit var progressBarUpcoming: ProgressBar
    private lateinit var progressBarFinished: ProgressBar
    private val carouselHandler = Handler(Looper.getMainLooper())
    private var currentCarouselPosition = 0

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(this)[HomeView::class.java]
        initializeRecyclerViews(view)
        setupAdapters()
        observeViewModel()
        return view
    }

    private fun initializeRecyclerViews(view: View) {
        upcomingRecyclerView = view.findViewById(R.id.rv_upcoming_events_home)
        finishedRecyclerView = view.findViewById(R.id.rv_finished_events_home)
        upcomingRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        finishedRecyclerView.layoutManager = context?.let { NonScrollableLinearLayoutManager(it) }

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(upcomingRecyclerView)
        progressBarUpcoming = view.findViewById(R.id.progress_bar_upcoming)
        progressBarFinished = view.findViewById(R.id.progress_bar_finished)
    }

    private fun setupAdapters() {
        upcomingEventAdapter = createCarouselEventAdapter()
        finishedEventAdapter = createRowEventAdapter()
        upcomingRecyclerView.adapter = upcomingEventAdapter
        finishedRecyclerView.adapter = finishedEventAdapter
    }

    private fun createCarouselEventAdapter(): EventAdapterU {
        return EventAdapterU(emptyList(), object : EventAdapterU.CarouselEventClickListener {
            override fun onCarouselEventClicked(upcomingEvent: ListEventsItem) {
                startActivity(createEventDetailIntent(upcomingEvent))
            }
        })
    }

    private val carouselRunnable = object : Runnable {
        override fun run() {
            if (upcomingEventAdapter.itemCount > 0) {
                currentCarouselPosition = (currentCarouselPosition + 1) % upcomingEventAdapter.itemCount
                upcomingRecyclerView.smoothScrollToPosition(currentCarouselPosition)
            }
            carouselHandler.postDelayed(this, 7000)
        }
    }

    private fun createRowEventAdapter(): EventAdapterF {
        return EventAdapterF(emptyList(), object : EventAdapterF.RowEventClickListener {
            override fun onRowEventClicked(finishedEvent: ListEventsItem) {
                startActivity(createEventDetailIntent(finishedEvent))
            }
        })
    }

    private fun createEventDetailIntent(event: ListEventsItem): Intent {
        return Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra("event.name", event.name)
            putExtra("event.mediaCover", event.mediaCover)
            putExtra("event.imageLogo", event.imageLogo)
            putExtra("event.summary", event.summary)
            putExtra("event.ownerName", event.ownerName)
            putExtra("event.description", event.description)
            putExtra("event.beginTime", event.beginTime)
            putExtra("event.registrants", event.registrants)
            putExtra("event.quota", event.quota)
            putExtra("event.link", event.link)
        }
    }

    private fun observeViewModel() {
        viewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            progressBarUpcoming.visibility = View.GONE
            upcomingEventAdapter.updateUpcomingEvents(events)
        }

        viewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            progressBarFinished.visibility = View.GONE
            finishedEventAdapter.updateFinishedEvents(events)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carouselHandler.post(carouselRunnable)

        progressBarUpcoming.visibility = View.VISIBLE
        progressBarFinished.visibility = View.VISIBLE
        viewModel.getUpcomingEvents()
        viewModel.getFinishedEvents()
    }
}
