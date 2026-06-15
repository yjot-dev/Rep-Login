package com.yjotdev.login.presentation.navigation

import android.text.SpannableString
import android.text.style.TabStopSpan
import kotlinx.coroutines.launch
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.yjotdev.login.MainActivity
import com.yjotdev.login.R

fun MainActivity.setupAppNavigation() {
    // Inicializa el NavController
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentNav) as NavHostFragment
    val navController = navHostFragment.navController
    // Vincula NavController con los BottomMenu
    binding.bottomMenu1.setupWithNavController(navController)
    binding.bottomMenu2.setupWithNavController(navController)
    // Logica para alternar la visibilidad de los BottomMenu
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.navigationLogin,
            R.id.navigationRegister,
            R.id.navigationRecovery -> {
                binding.bottomMenu1.visibility = View.VISIBLE
                binding.bottomMenu2.visibility = View.GONE
            }
            else -> {
                binding.bottomMenu1.visibility = View.GONE
                binding.bottomMenu2.visibility = View.VISIBLE
            }
        }
    }
}

fun MainActivity.observeViewModelState() {
    val loadingOverlay = findViewById<View>(R.id.loadingOverlay)
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentNav) as NavHostFragment
    val navController = navHostFragment.navController
    val context = this@observeViewModelState
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiState.collect { uiState ->
                loadingOverlay.isVisible = uiState.isLoading
            }
        }
    }
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.eventChannel.collect { event ->
                when (event) {
                    // Login -> Dashboard (Revisar UiViewModel.kt lineas 88 - 117)
                    // User -> Login (Revisar UiViewModel.kt lineas 78 - 84)
                    is UiEvent.Navigate -> navController.navigate(event.resId)
                    // Muestra un mensaje de exito o error en el Toast
                    is UiEvent.ShowToast -> Toast.makeText(
                        context, event.message, Toast.LENGTH_SHORT
                    ).show()
                    // Muestra el error en el Log
                    is UiEvent.ShowLog -> Log.d("Https",event.message)
                }
            }
        }
    }
}

fun RadioButton.justifyTextAtTheEdges(left: String, right: String) {
    this.post {
        val totalWidth = this.width
        if (totalWidth <= 0) return@post

        // 1. Usamos compoundPadding para descontar el área del círculo del RadioButton
        val availableWidth = totalWidth - this.compoundPaddingLeft - this.compoundPaddingRight

        // 2. Medimos exactamente cuánto espacio ocupa el texto de la derecha
        val rightTextWidth = this.paint.measureText(right)

        // 3. El tabStop debe ser el ancho disponible menos el ancho del texto B
        // Esto hace que el texto B comience en la posición exacta para quedar alineado a la derecha
        val tabStop = (availableWidth - rightTextWidth).toInt()

        // 4. Verificamos que el texto de la izquierda no invada el espacio del de la derecha
        if (tabStop > this.paint.measureText(left)) {
            val fullText = "$left\t$right"
            val spannable = SpannableString(fullText)

            spannable.setSpan(
                TabStopSpan.Standard(tabStop),
                0,
                fullText.length,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            this.text = spannable
        } else {
            // Fallback: si no caben ambos, los mostramos con un espacio normal
            val normalText = "$left $right"
            this.text = normalText
        }
    }
}