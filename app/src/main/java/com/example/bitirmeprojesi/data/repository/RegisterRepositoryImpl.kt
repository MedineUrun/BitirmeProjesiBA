package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.dao.UserDao
import com.example.bitirmeprojesi.data.entitiy.User
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val userDao: UserDao
):RegisterRepository {
    override suspend fun register(user: User): Long = userDao.insert(user)?.let {
        return it
    }?: run{
        return -2
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserByUserNameOrEmail(userName: String, email: String): User? {
        return userDao.getUserByUserNameOrEmail(userName, email)
    }
}