package com.yjotdev.login.application.navigation

import kotlinx.coroutines.launch
import android.util.Log
import android.view.View
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
    // Vincula NavController con el BottomMenu y TopMenu
    binding.bottomMenu1.setupWithNavController(navController)
    binding.bottomMenu2.setupWithNavController(navController)
    // Logica del BottomMenu
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.loginFragment,
            R.id.registerFragment,
            R.id.recoveryFragment -> {
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
                    // Login -> Dashboard (Revisar UiViewModel.kt lineas 87 - 89)
                    // User -> Login (Revisar UiViewModel.kt lineas 72 - 78)
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