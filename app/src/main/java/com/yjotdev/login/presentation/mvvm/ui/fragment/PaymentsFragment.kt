package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.databinding.FragmentPaymentsBinding
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.R

@AndroidEntryPoint
class PaymentsFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        binding.btnConfirmPayment.setOnClickListener {
            val selectedPlan = when (binding.rgPlans.checkedRadioButtonId) {
                R.id.rbBasic -> "basic"
                R.id.rbPremium -> "premium"
                R.id.rbEnterprise -> "enterprise"
                else -> null
            }

            context?.let { context ->
                if (selectedPlan != null) {
                    // Realizar la creacion de la orden
                    viewModel.createOrder(selectedPlan) { approveUrl ->
                        val customTabsIntent = CustomTabsIntent.Builder().build()
                        customTabsIntent.launchUrl(requireContext(), approveUrl.toUri())
                    }
                } else {
                    Toast.makeText(context,
                        getString(R.string.toast_payment_invalid),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}