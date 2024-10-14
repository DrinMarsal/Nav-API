package com.dicoding.myappp.models.event

import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.R
import com.bumptech.glide.Glide

class EventAdapterU(
    private var upcomingEvents: List<ListEventsItem>,
    private val listener: CarouselEventClickListener,
) : RecyclerView.Adapter<EventAdapterU.CarouselViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateUpcomingEvents(newUpcomingEvents: List<ListEventsItem>) {
        upcomingEvents = newUpcomingEvents
        notifyDataSetChanged()
    }

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rowIvMediaCover: ImageView = itemView.findViewById(R.id.iv_media_cover)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming, parent, false)
        return CarouselViewHolder(view)
    }

    override fun getItemCount(): Int = upcomingEvents.size

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val upcomingEvent = upcomingEvents[position]
        Glide.with(holder.itemView.context)
            .load(upcomingEvent.mediaCover)
            .into(holder.rowIvMediaCover)
        holder.itemView.setOnClickListener {
            listener.onCarouselEventClicked(upcomingEvent)
        }
    }

    interface CarouselEventClickListener {
        fun onCarouselEventClicked(upcomingEvent: ListEventsItem)
    }
}
