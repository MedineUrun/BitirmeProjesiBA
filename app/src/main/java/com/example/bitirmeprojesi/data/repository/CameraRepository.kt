package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.entitiy.Camera

interface CameraRepository {
    suspend fun insert(photo: Camera) :Long
}