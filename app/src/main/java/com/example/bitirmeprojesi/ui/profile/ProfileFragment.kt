package com.example.bitirmeprojesi.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.state.RegisterState
import com.example.bitirmeprojesi.data.state.UserUpdateState
import com.example.bitirmeprojesi.databinding.FragmentProfileBinding
import com.example.bitirmeprojesi.showAlert
import com.example.bitirmeprojesi.showSnackBar
import com.example.bitirmeprojesi.ui.login.LoginFragment
import com.example.bitirmeprojesi.ui.register.RegisterFragment
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by activityViewModels()
    private var userId: Int? = null
    private val GALLERY_REQUEST_CODE = 1001

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        listeners()
        observeUserUpdateState()

        val userEmail = arguments?.getString(LoginFragment.USER_EMAIL)
        val userName = arguments?.getString(LoginFragment.USER_NAME)
        val userPassword = arguments?.getString(LoginFragment.USER_PASSWORD)

        binding.etEmail.setText(userEmail)
        binding.etUserName.setText(userName)
        binding.etPassword.setText(userPassword)
        userId = arguments?.getInt(LoginFragment.USER_ID)

        val profileImage = binding.ivTwoProfile
        profileImage.setOnClickListener {
            openGallery()
        }

    }

    private fun listeners() {
        binding.btnUpdate.setOnClickListener{
            userId?.let { userIdValue ->
                viewModel.updateUser(
                    binding.etUserName.text.toString().trim(),
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                    userIdValue
                )
            }
        }
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = result.data?.data
            if (selectedImageUri != null) {
                // Seçilen resmi işlemek için kullanabilirsiniz
                // Örneğin, Room veritabanına kaydedebilir veya bir ImageView'da gösterebilirsiniz
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(galleryIntent)
    }
    private fun observeUserUpdateState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.userUpdateState.collect{
                    when(it){
                        is UserUpdateState.Idle->{}
                        is UserUpdateState.Success->{
                            requireActivity().showSnackBar(binding.btnUpdate,"Güncelleme Başarılı")
                        }
                        is UserUpdateState.Error->{
                            requireActivity().showAlert(R.string.upps_something_went_wrong)
                        }
                        is UserUpdateState.UserNotFound->{
                            requireActivity().showAlert(R.string.user_not_found)
                        }
                        is UserUpdateState.UserAlreadyExist->{
                            requireActivity().showAlert(R.string.user_info_already_exist)
                        }
                    }
                }
            }
        }
    }
}