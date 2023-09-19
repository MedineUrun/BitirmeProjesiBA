package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.dao.UserDao
import com.example.bitirmeprojesi.data.entitiy.User
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) :ProfileRepository {

    override suspend fun profileUpdate(user: User) {
        userDao.update(user)
    }

    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    override suspend fun getUserByUserNameAndEmail(
        userName: String,
        email: String,
    ): User? {
        return userDao.getUserByUserNameAndEmail(userName, email)
    }

    override suspend fun getUserByEmail(
        email: String,
    ): User? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getUserByUserNameOrEmail(userName: String, email: String) :User?{
        return userDao.getUserByUserNameOrEmail(userName,email)
    }


}