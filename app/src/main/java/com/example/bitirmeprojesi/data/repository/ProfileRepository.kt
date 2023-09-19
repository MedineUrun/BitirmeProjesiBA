package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.entitiy.User

interface ProfileRepository {
    suspend fun profileUpdate(user: User)
    suspend fun getUserById(id:Int):User?
    suspend fun getUserByUserNameAndEmail(userName: String, email: String):User?
    suspend fun getUserByUserNameOrEmail(userName: String, email: String) :User?
    suspend fun getUserByEmail(email: String):User?

}