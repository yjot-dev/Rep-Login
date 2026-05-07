package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.databinding.FragmentUserBinding
import com.yjotdev.login.R
import com.yjotdev.login.presentation.utils.Helper

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        val name = binding.inputName.text.toString()
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        binding.btnDelete.isEnabled = false
        binding.btnUpdate.isEnabled = false

        binding.btnSeePassword.setOnClickListener {
            if (binding.inputPassword.inputType == 129) {
                binding.btnSeePassword.setImageResource(R.drawable.hide_password)
                binding.inputPassword.inputType = 145
            } else {
                binding.btnSeePassword.setImageResource(R.drawable.show_password)
                binding.inputPassword.inputType = 129
            }
        }

        binding.btnSendCode.setOnClickListener {
            context?.let { context ->
                // Envia un código al email del usuario
                val subject = context.getString(R.string.email_subject2)
                viewModel.sendEmail(email, subject)
                showAlertDialog()
            }
        }

        binding.btnUpdate.setOnClickListener {
            context?.let { context ->
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    if (Helper.isValidUser(name)
                        && Helper.isValidEmail(email)
                        && Helper.isValidPassword(password)){
                        // Inicia una acción
                        viewModel.updateUser(name, email, password)
                    } else {
                        val text = context.getString(R.string.toast_invalid_data)
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val text = context.getString(R.string.toast_empty_fields)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
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

    private fun showAlertDialog() {
        val state = viewModel.uiState.value
        context?.let { context ->
            // Crear un EditText para ingresar solo números
            val input = EditText(requireContext()).apply {
                inputType = InputType.TYPE_CLASS_NUMBER
                hint = context.getString(R.string.input_code)
            }
            // Construir el AlertDialog
            AlertDialog.Builder(requireContext())
                .setTitle(context.getString(R.string.fragment_register_btn_code))
                .setView(input)
                .setPositiveButton(context.getString(R.string.alert_dialog_validate)) { dialog, _ ->
                    val code = input.text.toString()
                    if (code.isNotEmpty()) {
                        if (code == state.randomCode.toString()) {
                            // Inicia una acción
                            binding.btnDelete.isEnabled = true
                            binding.btnUpdate.isEnabled = true
                            dialog.dismiss()
                        } else {
                            val text = context.getString(R.string.toast_code_different)
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val text = context.getString(R.string.toast_empty_fields)
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton(context.getString(R.string.alert_dialog_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}