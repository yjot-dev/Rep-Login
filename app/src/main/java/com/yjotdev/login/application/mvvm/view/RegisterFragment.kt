package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import kotlin.getValue
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yjotdev.login.R
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.databinding.FragmentRegisterBinding
import com.yjotdev.login.application.mvvm.viewmodel.UiViewModel

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
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

        binding.btnRegister.setOnClickListener{
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                // Notifica al ViewModel los nuevos datos e inicia una acción
                viewModel.insertUser(name, email, password)
            }else{
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
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
                        viewModel.clearFlags()
                    }

                    if(uiState.wasInserted){
                        Toast.makeText(context, "Registro exitosa", Toast.LENGTH_SHORT).show()
                        viewModel.clearFlags()
                    }
                }
            }
        }
    }
}