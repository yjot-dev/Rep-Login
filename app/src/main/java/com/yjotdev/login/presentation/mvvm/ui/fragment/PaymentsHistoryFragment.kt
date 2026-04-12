package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.domain.model.PaymentModel
import com.yjotdev.login.databinding.FragmentPaymentsHistoryBinding
import com.yjotdev.login.presentation.mvvm.ui.adapter.PaymentAdapter

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
            PaymentModel(id = 1, amount = "$5.00", date = "2026-04-01", status = "Completado", 1),
            PaymentModel(id = 2, amount = "$10.00", date = "2026-03-28", status = "Completado", 1),
            PaymentModel(id = 3, amount = "$20.00", date = "2026-03-15", status = "Fallido", 1),
            PaymentModel(id = 4, amount = "$5.00", date = "2026-02-01", status = "Completado", 1),
            PaymentModel(id = 5, amount = "$10.00", date = "2026-02-28", status = "Completado", 1),
            PaymentModel(id = 6, amount = "$20.00", date = "2026-01-15", status = "Fallido", 1),
        )
        adapter.submitList(mockPayments)
    }
}