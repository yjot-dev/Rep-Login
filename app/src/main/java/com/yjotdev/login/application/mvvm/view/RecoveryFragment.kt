package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yjotdev.login.R
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import kotlinx.coroutines.launch
import com.yjotdev.login.databinding.FragmentRecoveryBinding
import com.yjotdev.login.application.mvvm.viewmodel.UserViewModel

@AndroidEntryPoint
class RecoveryFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentRecoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecoveryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModelState()
    }

    private fun setupClickListeners() {
        binding.btnSeePassword.setOnClickListener {
            if(binding.inputPassword.inputType == 129){
                binding.inputPassword.inputType = 145
            }else{
                binding.inputPassword.inputType = 129
            }
        }

        binding.btnCode.setOnClickListener{
            val email = binding.inputEmail.text.toString()

            if(email.isNotEmpty()){
                // Notifica al ViewModel los nuevos datos e inicia una acción
                viewModel.sendEmail(email)
            }else{
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnRecovery.setOnClickListener{_ ->
            val code = binding.inputCode.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if(code.isNotEmpty() && password.isNotEmpty()){
                // Notifica al ViewModel los nuevos datos e inicia una acción
                viewModel.recoveryPassword(code, email, password)
            }else{
                Toast.makeText(context, "Campos vacios o codigo incorrecto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeViewModelState() {
        val loadingOverlay = requireActivity().findViewById<View>(R.id.loadingOverlay)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    loadingOverlay.isVisible = uiState.isLoading

                    uiState.error?.let { errorMessage ->
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        viewModel.clearFlags() // Informa al ViewModel que el error ya fue mostrado
                    }

                    if(uiState.wasUpdated){
                        Toast.makeText(context, "Clave actualizada", Toast.LENGTH_SHORT).show()
                        viewModel.clearFlags()
                    }

                    if(uiState.wasEmailed){
                        Toast.makeText(context, "Correo electronico enviado", Toast.LENGTH_SHORT).show()
                        viewModel.clearFlags()
                    }
                }
            }
        }
    }
}