package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.model.Photo
import com.example.bitirmeprojesi.data.service.PhotoService
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoService: PhotoService
):PhotoRepository {

    override suspend fun getAllPhotos(category:String): List<Photo> {
        return photoService.getAllPhotos(category).photos
    }
}