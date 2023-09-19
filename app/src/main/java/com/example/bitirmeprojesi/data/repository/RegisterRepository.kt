package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.entitiy.User

interface RegisterRepository {
    suspend fun register(user: User): Long
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserByUserNameOrEmail(userName:String, email: String):User?
}