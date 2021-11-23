package com.example.busschedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.*

class BusStopAdapter(private val onItemClicked: (Schedule) -> Unit) :
    ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {

            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }

    class BusStopViewHolder(private val binding: BusStopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            @SuppressLint("SimpleDateFormat")
            private val dateFormat = SimpleDateFormat("h:mm a")
        }

        fun bind(schedule: Schedule) {
            val date = Date(schedule.arrivalTime.toLong() * 1000)
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text = dateFormat.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val binding = BusStopItemBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        val viewHolder = BusStopViewHolder(binding)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}