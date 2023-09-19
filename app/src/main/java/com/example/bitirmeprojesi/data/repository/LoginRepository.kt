package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.entitiy.User

interface LoginRepository {

    suspend fun login(userInfo: String, password: String): User?
    suspend fun loginWithEmail(email:String, password: String) :User?
    suspend fun loginWithUserName(userName:String, password: String) :User?

}