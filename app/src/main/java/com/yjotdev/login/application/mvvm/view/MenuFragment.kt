package com.yjotdev.login.application.mvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import com.yjotdev.login.application.navigation.Navigation
import com.yjotdev.login.databinding.FragmentMenuBinding
import com.yjotdev.login.MainActivity
import com.yjotdev.login.R

@AndroidEntryPoint
class MenuFragment : Fragment(), Navigation {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Navegación interna del menú
        setupClickListeners()
        // Navegación al Login por default
        if (savedInstanceState == null) {
            navigateTo(1)
        }
    }

    override fun navigateTo(destination: Int) {
        val fragmentManager = childFragmentManager
        val transaction = fragmentManager.beginTransaction()
        val fragmentToShow = when (destination) {
            1 -> LoginFragment()
            2 -> RegisterFragment()
            3 -> RecoveryFragment()
            4 -> {
                (requireActivity() as? MainActivity)?.replaceMenuWith(UserFragment())
                return // Detiene la ejecución para no hacer una transacción aquí
            }
            else -> null
        }
        fragmentToShow?.let {
            transaction.replace(R.id.fragmentAdmin, it)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun setupClickListeners(){
        binding.btnLoginMenu.setOnClickListener { navigateTo(1) }
        binding.btnRegisterMenu.setOnClickListener { navigateTo(2) }
        binding.btnRecoveryMenu.setOnClickListener { navigateTo(3) }
    }
}