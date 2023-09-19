package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.dao.CameraDao
import com.example.bitirmeprojesi.data.entitiy.Camera
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val cameraDao: CameraDao
):CameraRepository {
    override suspend fun insert(photo: Camera): Long {
        return cameraDao.insert(photo)
    }

}