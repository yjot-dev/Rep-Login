package com.yjotdev.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.databinding.ActivityMainBinding
import com.yjotdev.login.presentation.navigation.handlePaypalIntent
import com.yjotdev.login.presentation.navigation.observeViewModelState
import com.yjotdev.login.presentation.navigation.setupNavigation
import com.yjotdev.login.presentation.navigation.setupPermissions

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    internal val viewModel: UiViewModel by viewModels()
    internal lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configura la IU de la actividad
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Configura la navegación, bottomMenu y edge-to-edge
        setupNavigation()
        // solicita permisos necesarios
        setupPermissions()
        // Observacion de estados del ViewModel
        observeViewModelState()
        // Procesa el intent inicial
        intent.data?.let { uri -> handlePaypalIntent(uri) }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Procesa el intent inicial
        intent.data?.let { uri -> handlePaypalIntent(uri) }
    }
}