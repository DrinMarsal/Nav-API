package com.dicoding.myappp.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ProgressBar
import android.widget.EditText
import com.dicoding.myappp.models.event.EventClickListener
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.ui.viewss.UpcomingView
import com.dicoding.myappp.models.event.EventAdapter
import com.dicoding.myappp.ui.DetailActivity
import com.dicoding.myappp.R

class UpcomingFragment : Fragment() {

    private lateinit var adapter: EventAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: UpcomingView
    private lateinit var searchEdit: EditText
    private lateinit var progressBar: ProgressBar

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming, container, false)
        progressBar = view.findViewById(R.id.progress_bar)

        setupRecyclerView(view)
        setupViewModel()
        setupSearchEdit(view)
        return view
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rv_events_upcoming)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = EventAdapter(emptyList(), object : EventClickListener {
            override fun onEventClicked(event: ListEventsItem) {
                startActivity(createEventDetailIntent(event))
            }
        })
        recyclerView.adapter = adapter
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[UpcomingView::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            adapter.updateEvents(events)
        }

        viewModel.getUpcomingEvents()
    }


    private fun setupSearchEdit(view: View) {
        searchEdit = view.findViewById(R.id.search_edit_upcoming)
        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    viewModel.getUpcomingEvents()
                } else {
                    viewModel.searchUpcomingEvents(s.toString())
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
