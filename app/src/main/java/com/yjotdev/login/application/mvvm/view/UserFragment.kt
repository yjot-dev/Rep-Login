package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.application.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.databinding.FragmentUserBinding

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModelState()
    }

    private fun setupClickListeners() {
        binding.btnSeePassword.setOnClickListener {
            if (binding.inputPassword.inputType == 129) {
                binding.inputPassword.inputType = 145
            } else {
                binding.inputPassword.inputType = 129
            }
        }

        binding.btnUpdate.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Notifica al ViewModel los nuevos datos e inicia una acción
                viewModel.updateUser(name, email, password)
            } else {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteUser()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
        }
    }

    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    uiState.user?.let { user ->
                        if(binding.inputName.text.toString() != user.name &&
                            binding.inputEmail.text.toString() != user.email &&
                            binding.inputPassword.text.toString() != user.password)
                        {
                            binding.inputName.setText(user.name)
                            binding.inputEmail.setText(user.email)
                            binding.inputPassword.setText(user.password)
                        }
                    }
                }
            }
        }
    }
}