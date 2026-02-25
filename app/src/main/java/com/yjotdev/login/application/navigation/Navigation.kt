package com.yjotdev.login.application.navigation

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.coroutines.launch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
    // Vincula NavController con el BottomMenu
    binding.bottomMenu.setupWithNavController(navController)
    // Logica del BottomMenu
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.loginFragment,
            R.id.registerFragment,
            R.id.recoveryFragment -> {
                binding.bottomMenu.visibility = View.VISIBLE
            }
            else -> {
                binding.bottomMenu.visibility = View.GONE
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
                    // Login -> User (Revisar UiViewModel.kt lineas 87 - 89)
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

fun MainActivity.setupAppPermissions() {
    val context = this@setupAppPermissions
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, context.getString(R.string.toast_permission_granted), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, context.getString(R.string.toast_permission_denied), Toast.LENGTH_LONG).show()
        }
    }
    if (PackageManager.PERMISSION_GRANTED ==
        ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)){
        Toast.makeText(context, context.getString(R.string.toast_permission_1), Toast.LENGTH_SHORT).show()
    }else{
        requestPermissionLauncher.launch(Manifest.permission.INTERNET)
    }
    if (PackageManager.PERMISSION_GRANTED ==
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)){
        Toast.makeText(context, context.getString(R.string.toast_permission_2), Toast.LENGTH_SHORT).show()
    }else{
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_NETWORK_STATE)
    }
}

fun MainActivity.hasPermissions(): Boolean {
    val context = this@hasPermissions
    val permissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE
    )
    return permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }
}