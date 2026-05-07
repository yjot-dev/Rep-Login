package com.yjotdev.login.presentation.mvvm.ui.fragment

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.yjotdev.login.databinding.FragmentRecoveryBinding
import com.yjotdev.login.presentation.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.R
import com.yjotdev.login.presentation.utils.Helper

@AndroidEntryPoint
class RecoveryFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private var _binding: FragmentRecoveryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners() {
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        binding.btnRecovery.isEnabled = false

        binding.btnSeePassword.setOnClickListener {
            if(binding.inputPassword.inputType == 129){
                binding.btnSeePassword.setImageResource(R.drawable.hide_password)
                binding.inputPassword.inputType = 145
            }else{
                binding.btnSeePassword.setImageResource(R.drawable.show_password)
                binding.inputPassword.inputType = 129
            }
        }

        binding.btnSendCode.setOnClickListener {
            context?.let { context ->
                if(email.isNotEmpty()){
                    if (Helper.isValidEmail(email)) {
                        // Envia un código al email del usuario
                        val subject = context.getString(R.string.email_subject1)
                        viewModel.sendEmail(email, subject)
                        showAlertDialog()
                    } else {
                        val text = context.getString(R.string.toast_invalid_data)
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    val text = context.getString(R.string.toast_empty_fields)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnRecovery.setOnClickListener {
            context?.let { context ->
                if(email.isNotEmpty() && password.isNotEmpty()){
                    if (Helper.isValidEmail(email)
                        && Helper.isValidPassword(password)){
                        // Inicia una acción
                        viewModel.changePasswordUser(email, password)
                    } else {
                        val text = context.getString(R.string.toast_invalid_data)
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    val text = context.getString(R.string.toast_empty_fields)
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
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
                .setTitle(context.getString(R.string.fragment_recovery_btn_code))
                .setView(input)
                .setPositiveButton(context.getString(R.string.alert_dialog_validate)) { dialog, _ ->
                    val code = input.text.toString()
                    if (code.isNotEmpty()) {
                        if (code == state.randomCode.toString()) {
                            // Inicia una acción
                            binding.btnRecovery.isEnabled = true
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