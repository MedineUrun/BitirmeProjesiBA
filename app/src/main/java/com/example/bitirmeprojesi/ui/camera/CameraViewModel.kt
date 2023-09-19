package com.example.bitirmeprojesi.ui.camera

import androidx.lifecycle.ViewModel
import com.example.bitirmeprojesi.data.repository.CameraRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val cameraRepository: CameraRepository
):ViewModel() {


}