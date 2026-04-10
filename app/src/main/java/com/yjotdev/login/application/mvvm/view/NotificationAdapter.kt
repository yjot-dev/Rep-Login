package com.yjotdev.login.application.mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yjotdev.login.domain.entity.NotificationEntity
import com.yjotdev.login.R

class NotificationAdapter :
    ListAdapter<NotificationEntity, NotificationAdapter.NotificationViewHolder>(DiffCallback()) {

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

        fun bind(notification: NotificationEntity) {
            tvMessage.text = notification.message
            tvDate.text = notification.date
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NotificationEntity>() {
        override fun areItemsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: NotificationEntity, newItem: NotificationEntity) = oldItem == newItem
    }
}