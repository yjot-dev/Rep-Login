package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.domain.entity.NotificationEntity
import com.yjotdev.login.databinding.FragmentNotificationsBinding

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
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
        adapter = NotificationAdapter()
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotifications.adapter = adapter

        // TODO: Consumir API de backend (Node.js + MySQL + Stripe) para obtener notificaciones reales
        // Datos mock para probar la UI
        val mockNotifications = listOf(
            NotificationEntity(id = "1", message = "Pago de $5.00 completado", date = "2026-04-01"),
            NotificationEntity(id = "2", message = "Nueva reunión disponible en Zoom", date = "2026-04-02"),
            NotificationEntity(id = "3", message = "Suscripción Premium renovada", date = "2026-04-03")
        )
        adapter.submitList(mockNotifications)
    }
}