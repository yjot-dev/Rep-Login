package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.databinding.FragmentPaymentsBinding
import com.yjotdev.login.R

@AndroidEntryPoint
class PaymentsFragment : Fragment() {

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
                R.id.rbBasic -> getString(R.string.plan1_payments)
                R.id.rbPremium -> getString(R.string.plan2_payments)
                R.id.rbEnterprise -> getString(R.string.plan3_payments)
                else -> null
            }

            context?.let { context ->
                if (selectedPlan != null) {
                    // TODO: Integrar con backend Node.js + Stripe para procesar el pago
                    Toast.makeText(context,
                        getString(R.string.toast_payment_valid, selectedPlan),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,
                        getString(R.string.toast_payment_invalid),
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}