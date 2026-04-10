package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.domain.entity.PaymentEntity
import com.yjotdev.login.databinding.FragmentPaymentsHistoryBinding

@AndroidEntryPoint
class PaymentsHistoryFragment : Fragment() {

    private var _binding: FragmentPaymentsHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadAdapter() {
        // Inicializar RecyclerView
        adapter = PaymentAdapter()
        binding.rvPaymentsHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPaymentsHistory.adapter = adapter

        // TODO: Consumir API de backend (Node.js + Stripe + MySQL) para obtener historial real
        // Por ahora, datos mock para UI
        val mockPayments = listOf(
            PaymentEntity(id = "1", amount = "$5.00", date = "2026-04-01", status = "Completado"),
            PaymentEntity(id = "2", amount = "$10.00", date = "2026-03-28", status = "Completado"),
            PaymentEntity(id = "3", amount = "$20.00", date = "2026-03-15", status = "Fallido"),
            PaymentEntity(id = "4", amount = "$5.00", date = "2026-02-01", status = "Completado"),
            PaymentEntity(id = "5", amount = "$10.00", date = "2026-02-28", status = "Completado"),
            PaymentEntity(id = "6", amount = "$20.00", date = "2026-01-15", status = "Fallido"),
        )
        adapter.submitList(mockPayments)
    }
}