package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.model.Photo

interface PhotoRepository {

    suspend fun getAllPhotos(category:String):List<Photo>

}