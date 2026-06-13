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
import java.util.Locale
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.databinding.FragmentPaymentsBinding
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.presentation.utils.Helper.moneyFormatByCountry
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
        val countryCode = Locale.getDefault().country
        val moneyCode = moneyFormatByCountry(countryCode).second
        binding.rbTest.text = this.getString(R.string.fragment_payments_rb_test, moneyFormatByCountry(countryCode).first)
        binding.rbLv1Support.text = this.getString(R.string.fragment_payments_rb_lv1support, moneyFormatByCountry(countryCode).first)
        binding.rbLv2Support.text = this.getString(R.string.fragment_payments_rb_lv2support, moneyFormatByCountry(countryCode).first)

        binding.btnConfirmPayment.setOnClickListener {
            val selectedPlan = when (binding.rgPlans.checkedRadioButtonId) {
                R.id.rbTest -> "test"
                R.id.rbLv1Support -> "lv1-support"
                R.id.rbLv2Support -> "lv2-support"
                else -> null
            }

            context?.let { context ->
                if (selectedPlan != null) {
                    // Realizar la creacion de la orden
                    viewModel.createOrder(selectedPlan, moneyCode) { approveUrl ->
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