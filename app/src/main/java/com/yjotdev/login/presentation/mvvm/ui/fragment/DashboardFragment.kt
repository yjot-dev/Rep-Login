package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.yjotdev.login.databinding.FragmentDashboardBinding
import com.yjotdev.login.presentation.mvvm.ui.adapter.PaymentAdapter
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.R

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
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
        setupRecyclerView()
        observeViewModelState()
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

    private fun setupRecyclerView() {
        // Inicializar RecyclerView
        adapter = PaymentAdapter()
        binding.rvPayments.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPayments.adapter = adapter

        // Iniciar corrutina para observar cambios en la lista de pagos
        viewModel.selectPayments(maxRows = 3)
    }

    private fun observeViewModelState(){
        val overlay = requireActivity().findViewById<View>(R.id.loadingOverlay)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.payments.isNotEmpty()) {
                        //Guarda resultados en el adapter
                        adapter.submitList(uiState.payments)
                    }
                    overlay.visibility = if (uiState.isLoading) {View.VISIBLE} else {View.GONE}
                }
            }
        }
    }
}