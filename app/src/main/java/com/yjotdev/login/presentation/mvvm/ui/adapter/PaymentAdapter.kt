package com.yjotdev.login.presentation.mvvm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.R

class PaymentAdapter :

    ListAdapter<PaymentModel, PaymentAdapter.PaymentViewHolder>(DiffCallback()) {

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

        fun bind(payment: PaymentModel) {
            val amount = "${payment.amount} ${payment.money}"
            val status = itemView.context.getString(R.string.fragment_payments_history_status)
            tvAmount.text = amount
            tvDate.text = payment.date
            tvStatus.text = status
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PaymentModel>() {
        override fun areItemsTheSame(oldItem: PaymentModel, newItem: PaymentModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PaymentModel, newItem: PaymentModel) = oldItem == newItem
    }
}