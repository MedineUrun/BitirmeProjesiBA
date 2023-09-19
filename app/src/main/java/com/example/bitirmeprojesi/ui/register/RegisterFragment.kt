package com.example.bitirmeprojesi.ui.register

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
import com.example.bitirmeprojesi.data.state.LoginState
import com.example.bitirmeprojesi.data.state.RegisterMessageState
import com.example.bitirmeprojesi.data.state.RegisterState
import com.example.bitirmeprojesi.databinding.FragmentRegisterBinding
import com.example.bitirmeprojesi.showAlert
import com.example.bitirmeprojesi.showToast
import com.example.bitirmeprojesi.ui.login.LoginFragment
import kotlinx.coroutines.launch


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private val viewModel: RegisterViewModel by activityViewModels()
    lateinit var binding: FragmentRegisterBinding

    companion object{
        const val USER_ID = "userId"
        const val USER_EMAIL = "userEmail"
        const val USER_NAME = "userName"
        const val USER_PASSWORD = "userPassword"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)

        listeners()
        observeMessage()
        observeUserAddState()

    }

    private fun listeners() {
        binding.btnRegister.setOnClickListener{
            viewModel.insert(
                binding.etUserName.text.toString().trim(),
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim(),
                binding.etConfirm.text.toString().trim())
        }
    }


    private fun observeMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    when(it){
                        RegisterMessageState.Idle ->{}
                        RegisterMessageState.UserAlreadyExists->{
                            requireActivity().showAlert(R.string.user_already_exists)
                        }
                        RegisterMessageState.Success ->{
                            requireActivity().showToast(R.string.register_successful)
                        }
                        RegisterMessageState.Empty ->{
                            requireActivity().showAlert(R.string.fill_in_the_blank)
                        }
                        RegisterMessageState.PasswordsNotEquals ->{
                            requireActivity().showAlert(R.string.password_not_equal)
                        }
                        RegisterMessageState.UserNameAndEmailAlreadyUsage ->{
                            requireActivity().showAlert(R.string.user_name_or_email_already_usage)
                        }
                        RegisterMessageState.InvalidEmail->{
                            requireActivity().showAlert(R.string.invalid_email)
                        }
                    }
                }
            }
        }
    }


    private fun observeUserAddState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.registerState.collect{
                    when(it){
                        RegisterState.Idle ->{}
                        RegisterState.Loading ->{

                        }
                        is RegisterState.Success->{
                        findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment,
                            bundleOf(
                                LoginFragment.USER_EMAIL to it.user.email,
                                LoginFragment.USER_NAME to it.user.userName,
                                LoginFragment.USER_PASSWORD to it.user.password,
                                LoginFragment.USER_ID to it.user.id
                            )
                        )
                        activity?.showToast(getString(R.string.user_login_success))
                    }
                        is RegisterState.Success ->{
                            findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
                        }
                        is RegisterState.Error ->{
                            requireActivity().showAlert(R.string.somethings_wrong)
                        }
                    }
                }
            }
        }
    }


}