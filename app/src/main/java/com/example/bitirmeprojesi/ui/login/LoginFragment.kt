package com.example.bitirmeprojesi.ui.login

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.state.LoginMessageState
import com.example.bitirmeprojesi.data.state.LoginState
import com.example.bitirmeprojesi.databinding.FragmentLoginBinding
import com.example.bitirmeprojesi.showAlert
import com.example.bitirmeprojesi.showSnackBar
import com.example.bitirmeprojesi.showToast
import com.example.bitirmeprojesi.ui.dashboard.DashboardFragment
import kotlinx.coroutines.launch


class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel:LoginViewModel by activityViewModels()

    companion object{
        const val USER_ID = "userId"
        const val USER_EMAIL = "userEmail"
        const val USER_NAME = "userName"
        const val USER_PASSWORD = "userPassword"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        listeners()
        observeLogin()
        observeMessage()
    }


    private fun observeLogin() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.loginState.collect{
                    when(it){
                        is LoginState.Idle->{}
                        is LoginState.Loading->{}
                        is LoginState.Result->{
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment,
                                bundleOf(
                                    USER_EMAIL to it.user.email,
                                    USER_NAME to it.user.userName,
                                    USER_PASSWORD to it.user.password,
                                    USER_ID to it.user.id
                                )
                                )
                            activity?.showToast(getString(R.string.user_login_success))
                        }
                        is LoginState.Error->{}
                    }
                }
            }
        }
    }

    private fun observeMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    when(it){
                        LoginMessageState.Idle ->{}
                        LoginMessageState.Empty ->{
                            requireActivity().showSnackBar(binding.btnLogin, "Kullanıcı bilgisi boş")
                        }
                        LoginMessageState.UserNotFound ->{
                            showUserNotFoundDialog()
                        }
                        LoginMessageState.InformationWrong ->{
                            requireActivity().showAlert(R.string.user_information_wrong)
                        }
                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.editTextUserInfo.text.toString().trim(),
                binding.editTextPassword.text.toString().trim()
            )
        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun showUserNotFoundDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Kullanıcı Bulunamadı")
            .setMessage("Belirtilen kullanıcı bulunamadı. Lütfen tekrar deneyin.")
            .setPositiveButton("Kayıt Ol") { _, _ ->
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            .setNegativeButton("İptal") { _, _ ->
            }
            .create()
        dialog.show()
    }


}