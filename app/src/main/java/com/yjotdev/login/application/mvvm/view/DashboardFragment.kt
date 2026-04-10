package com.yjotdev.login.application.mvvm.view

import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.domain.entity.PaymentEntity
import com.yjotdev.login.databinding.FragmentDashboardBinding

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PaymentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        loadAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.btnZoom.setOnClickListener {
            openAppOrPlay("us.zoom.videomeetings", "market://details?id=us.zoom.videomeetings")
        }

        binding.btnMeet.setOnClickListener {
            openAppOrPlay("com.google.android.apps.meetings", "market://details?id=com.google.android.apps.meetings")
        }
    }

    private fun openAppOrPlay(packageName: String, playUrl: String) {
        val intent = requireContext().packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            startActivity(intent)
        } else {
            val playIntent = Intent(Intent.ACTION_VIEW, playUrl.toUri())
            startActivity(playIntent)
        }
    }

    private fun loadAdapter() {
        // Inicializar RecyclerView
        adapter = PaymentAdapter()
        binding.rvPayments.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPayments.adapter = adapter

        // TODO: Consumir API de backend (Node.js + Stripe + MySQL) para obtener los 3 primeros registros del historial real
        // Por ahora, datos mock para UI
        val mockPayments = listOf(
            PaymentEntity(id = "1", amount = "$5.00", date = "2026-04-01", status = "Completado"),
            PaymentEntity(id = "2", amount = "$10.00", date = "2026-03-28", status = "Completado"),
            PaymentEntity(id = "3", amount = "$20.00", date = "2026-03-15", status = "Fallido")
        )
        adapter.submitList(mockPayments)
    }
}