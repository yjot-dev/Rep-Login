package com.yjotdev.login.application.mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yjotdev.login.domain.entity.PaymentEntity
import com.yjotdev.login.R

class PaymentAdapter :

    ListAdapter<PaymentEntity, PaymentAdapter.PaymentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_payment, parent, false)
        return PaymentViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

        fun bind(payment: PaymentEntity) {
            tvAmount.text = payment.amount
            tvDate.text = payment.date
            tvStatus.text = payment.status
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PaymentEntity>() {
        override fun areItemsTheSame(oldItem: PaymentEntity, newItem: PaymentEntity) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PaymentEntity, newItem: PaymentEntity) = oldItem == newItem
    }
}