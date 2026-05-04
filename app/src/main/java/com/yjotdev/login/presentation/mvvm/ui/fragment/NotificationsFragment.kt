package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.yjotdev.login.databinding.FragmentNotificationsBinding
import com.yjotdev.login.presentation.mvvm.ui.adapter.NotificationAdapter
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.R

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
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
        setupRecyclerView()
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        // Inicializar RecyclerView
        adapter = NotificationAdapter()
        binding.rvNotifications.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotifications.adapter = adapter

        // Iniciar corrutina para observar cambios en la lista de pagos
        viewModel.selectNotifications()
    }

    private fun observeViewModelState() {
        val overlay = requireActivity().findViewById<View>(R.id.loadingOverlay)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.notifications.isNotEmpty()) {
                        //Guarda resultados en el adapter
                        adapter.submitList(uiState.notifications)
                    }
                    overlay.visibility = if (uiState.isLoading) {View.VISIBLE} else {View.GONE}
                }
            }
        }
    }
}