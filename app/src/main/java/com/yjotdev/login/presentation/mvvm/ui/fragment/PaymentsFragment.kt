package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.databinding.FragmentPaymentsBinding
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.presentation.utils.Helper.moneyCodeByCountry
import com.yjotdev.login.presentation.utils.Helper.moneyConvertString
import com.yjotdev.login.presentation.utils.Helper.getAmountFromProductId
import com.yjotdev.login.presentation.utils.Helper.getLocalCountry
import com.yjotdev.login.presentation.utils.Helper.getLocalDate
import com.yjotdev.login.presentation.navigation.justifyTextAtTheEdges
import com.yjotdev.login.R

@AndroidEntryPoint
class PaymentsFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private var _binding: FragmentPaymentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var billingClient: BillingClient
    private var isBillingClientReady = false
    private lateinit var countryCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryCode = getLocalCountry()
        setupBillingClient()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::billingClient.isInitialized) {
            billingClient.endConnection()
        }
        _binding = null
    }

    private fun setupBillingClient() {
        val pendingParams = PendingPurchasesParams.newBuilder()
            .enableOneTimeProducts()
            .build()

        billingClient = BillingClient.newBuilder(requireContext())
            .setListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        // A. ENVIAR A VALIDAR AL BACKEND
                        val boughtProductId = purchase.products.firstOrNull()
                        viewModel.validatePayment(
                            purchaseToken = purchase.purchaseToken,
                            productId = boughtProductId,
                            amount = getAmountFromProductId(boughtProductId),
                            money = moneyCodeByCountry(countryCode),
                            date = getLocalDate()
                        )
                        // B. CONSUMIR EL PRODUCTO
                        handleConsumption(purchase)
                    }
                } else if (billingResult.responseCode != BillingClient.BillingResponseCode.USER_CANCELED) {
                    Log.e("Billing", "Error en la compra: ${billingResult.debugMessage}")
                }
            }
            .enablePendingPurchases(pendingParams)
            .build()

        startBillingConnection()
    }

    private fun startBillingConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    isBillingClientReady = true
                    consumePendingPurchases()
                } else {
                    Log.e("Billing", "Error de conexión: ${billingResult.debugMessage}")
                }
            }

            override fun onBillingServiceDisconnected() {
                isBillingClientReady = false
                billingClient.startConnection(this)
            }
        })
    }

    private fun setupClickListeners() {
        val moneyCode = moneyCodeByCountry(countryCode)
        binding.rbSupportLv1.justifyTextAtTheEdges(
            this.getString(R.string.fragment_payments_rb_support_lv1),
            moneyConvertString(1.0f, moneyCode)
        )
        binding.rbSupportLv2.justifyTextAtTheEdges(
            this.getString(R.string.fragment_payments_rb_support_lv2),
            moneyConvertString(5.0f, moneyCode)
        )
        binding.rbSupportLv3.justifyTextAtTheEdges(
            this.getString(R.string.fragment_payments_rb_support_lv3),
            moneyConvertString(10.0f, moneyCode)
        )

        binding.btnConfirmPayment.setOnClickListener {
            if (!isBillingClientReady) {
                startBillingConnection()
                return@setOnClickListener
            }

            val productId = when (binding.rgPlans.checkedRadioButtonId) {
                R.id.rbSupportLv1 -> "support_lv1"
                R.id.rbSupportLv2 -> "support_lv2"
                R.id.rbSupportLv3 -> "support_lv3"
                else -> null
            }

            context?.let { context ->
                if (productId != null) {
                    queryProductsAndLaunchFlow(productId)
                } else {
                    Toast.makeText(context, R.string.toast_payment_invalid, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun queryProductsAndLaunchFlow(productId: String) {
        // 1. Consultar detalles del producto en Play Console
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(BillingClient.ProductType.INAPP)
                        .build()
                )
            )
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, product ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && product.productDetailsList.isNotEmpty()) {
                val productDetails = product.productDetailsList[0]

                // 2 Preparar flujo de compra
                val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
                    .setProductDetails(productDetails)
                    .build()

                val billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(listOf(productDetailsParams))
                    .build()

                // 3 Lanzar el flujo de compra nativo
                billingClient.launchBillingFlow(requireActivity(), billingFlowParams)
            } else {
                Toast.makeText(context, R.string.toast_error_search_product, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun consumePendingPurchases() {
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()

        billingClient.queryPurchasesAsync(params) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in purchases) {
                    handleConsumption(purchase)
                }
            }
        }
    }

    private fun handleConsumption(purchase: Purchase) {
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            val consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

            billingClient.consumeAsync(consumeParams) { result, _ ->
                if (result.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d("Billing", "Producto consumido exitosamente: ${purchase.products.firstOrNull()}")
                } else {
                    Log.e("Billing", "Error al consumir: ${result.debugMessage}")
                }
            }
        }
    }
}