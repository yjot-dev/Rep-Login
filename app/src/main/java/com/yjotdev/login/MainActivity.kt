package com.yjotdev.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.databinding.ActivityMainBinding
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
    }
}