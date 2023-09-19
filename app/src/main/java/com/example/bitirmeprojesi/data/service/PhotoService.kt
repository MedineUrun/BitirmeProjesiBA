package com.example.bitirmeprojesi.data.service

import com.example.bitirmeprojesi.Constants
import com.example.bitirmeprojesi.data.model.Photo
import com.example.bitirmeprojesi.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotoService {

    @Headers(
        "Authorization: ${Constants.API_KEY}"
    )

    @GET("v1/search")
    suspend fun getAllPhotos(@Query("query") queryText:String): Response<List<Photo>>


}