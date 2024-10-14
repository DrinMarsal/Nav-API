package com.dicoding.myappp.models.event

import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dicoding.myappp.databinding.RecyclerViewBinding
import com.dicoding.myappp.models.response.ListEventsItem
import com.bumptech.glide.Glide

class EventAdapter(
    private var events: List<ListEventsItem>,
    private val listener: EventClickListener
) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    // ViewHolder menggunakan ViewBinding
    inner class ViewHolder(val binding: RecyclerViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.binding.tvEventName.text = event.name
        holder.binding.tvEventSummary.text = event.summary
        Glide.with(holder.itemView.context)
            .load(event.mediaCover)
            .into(holder.binding.ivMediaCover)

        holder.itemView.setOnClickListener {
            listener.onEventClicked(event)
        }
    }

    override fun getItemCount(): Int {
        return minOf(events.size, 5)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateEvents(newEvents: List<ListEventsItem>) {
        events = newEvents
        notifyDataSetChanged()
    }
}

interface EventClickListener {
    fun onEventClicked(event: ListEventsItem)
}