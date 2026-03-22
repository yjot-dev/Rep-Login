package com.yjotdev.login.application.mvvm.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.application.mvvm.viewmodel.UiViewModel
import com.yjotdev.login.databinding.FragmentLoginBinding
import com.yjotdev.login.application.utils.Helper
import com.yjotdev.login.R

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: UiViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnSeePassword.setOnClickListener {
            if(binding.inputPassword.inputType == 129){
                binding.inputPassword.inputType = 145
            }else{
                binding.inputPassword.inputType = 129
            }
        }

        binding.btnLogin.setOnClickListener{
            val nameOrEmail = binding.inputName.text.toString()
            val password = binding.inputPassword.text.toString()

            context?.let { context ->
                if (nameOrEmail.isNotEmpty() && password.isNotEmpty()) {
                    if (Helper.isValidUserOrEmail(nameOrEmail)
                        && Helper.isValidPassword(password)){
                        // Notifica al ViewModel los nuevos datos e inicia una acción
                        viewModel.loginUser(nameOrEmail, password)
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
    }
}