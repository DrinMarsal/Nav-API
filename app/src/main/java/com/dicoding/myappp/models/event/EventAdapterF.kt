package com.dicoding.myappp.models.event

import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dicoding.myappp.models.response.ListEventsItem
import com.dicoding.myappp.R
import com.bumptech.glide.Glide

class EventAdapterF(
    private var finishedEvents: List<ListEventsItem>,
    private val rowListener: RowEventClickListener,
) : RecyclerView.Adapter<EventAdapterF.RowViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateFinishedEvents(newFinishedEvents: List<ListEventsItem>) {
        finishedEvents = newFinishedEvents.take(5)
        notifyDataSetChanged()
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rowIvMediaCover: ImageView = itemView.findViewById(R.id.iv_media_cover)
        val rowTvEventName: TextView = itemView.findViewById(R.id.tv_event_name)
        val rowTvEventSummary: TextView = itemView.findViewById(R.id.tv_event_summary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_finished, parent, false)
        return RowViewHolder(view)
    }

    override fun getItemCount(): Int = finishedEvents.size

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val finishedEvent = finishedEvents[position]
        holder.rowTvEventName.text = finishedEvent.name
        holder.rowTvEventSummary.text = finishedEvent.summary
        Glide.with(holder.itemView.context)
            .load(finishedEvent.mediaCover)
            .into(holder.rowIvMediaCover)
        holder.itemView.setOnClickListener {
            rowListener.onRowEventClicked(finishedEvent)
        }
    }

    interface RowEventClickListener {
        fun onRowEventClicked(finishedEvent: ListEventsItem)
    }
}