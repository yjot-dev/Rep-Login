package com.yjotdev.login.presentation.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yjotdev.login.domain.model.NotificationModel
import com.yjotdev.login.R

class NotificationAdapter :
    ListAdapter<NotificationModel, NotificationAdapter.NotificationViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)

        fun bind(notification: NotificationModel) {
            tvMessage.text = notification.message
            tvDate.text = notification.date
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NotificationModel>() {
        override fun areItemsTheSame(oldItem: NotificationModel, newItem: NotificationModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: NotificationModel, newItem: NotificationModel) = oldItem == newItem
    }
}