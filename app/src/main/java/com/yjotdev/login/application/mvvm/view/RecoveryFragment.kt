package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
import com.yjotdev.login.databinding.FragmentRecoveryBinding
import com.yjotdev.login.application.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.R
import com.yjotdev.login.application.utils.Helper

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
        binding.btnSeePassword.setOnClickListener {
            if(binding.inputPassword.inputType == 129){
                binding.btnSeePassword.setImageResource(R.drawable.hide_password)
                binding.inputPassword.inputType = 145
            }else{
                binding.btnSeePassword.setImageResource(R.drawable.show_password)
                binding.inputPassword.inputType = 129
            }
        }

        binding.btnCode.setOnClickListener{
            val email = binding.inputEmail.text.toString()

            context?.let { context ->
                if(email.isNotEmpty()){
                    if (Helper.isValidEmail(email)) {
                        // Notifica al ViewModel los nuevos datos e inicia una acción
                        val subject = context.getString(R.string.email_subject1)
                        viewModel.sendEmail(email, subject)
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

        binding.btnRecovery.setOnClickListener{_ ->
            val code = binding.inputCode.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val state = viewModel.uiState.value

            context?.let { context ->
                if(code.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                    if (Helper.isValidNumber(code)
                        && Helper.isValidEmail(email)
                        && Helper.isValidPassword(password)){
                        if (code == state.randomCode.toString()) {
                            // Notifica al ViewModel los nuevos datos e inicia una acción
                            viewModel.changePasswordUser(email, password)
                        } else {
                            val text = context.getString(R.string.toast_code_different)
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                        }
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
}