package com.example.bitirmeprojesi.ui.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.databinding.FragmentCameraBinding
import com.example.bitirmeprojesi.databinding.FragmentProfileBinding
import com.example.bitirmeprojesi.ui.dashboard.DashboardViewModel
import com.example.bitirmeprojesi.ui.login.LoginFragment

class CameraFragment : Fragment(R.layout.fragment_camera) {
    private lateinit var binding: FragmentCameraBinding
    private val viewModel: CameraViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCameraBinding.bind(view)

        val takePhotoButton = view.findViewById<Button>(R.id.takePhotoButton)
        takePhotoButton.setOnClickListener {
            capturePhoto()
        }

    }

    private fun capturePhoto() {
        // Fotoğraf çekme işlemlerini burada gerçekleştirin.
    }


}