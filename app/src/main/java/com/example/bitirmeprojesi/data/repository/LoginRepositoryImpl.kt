package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.dao.UserDao
import com.example.bitirmeprojesi.data.entitiy.User
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val userDao: UserDao) :LoginRepository {

    override suspend fun login(userInfo: String, password: String): User? {
        userDao.getUserByUserInfoAndPassword(userInfo, password)?.let {
            return it
        } ?: run {
            return null
        }
    }

    override suspend fun loginWithEmail(email: String, password: String): User? {
        userDao.getUserByEmailAndPassword(email, password)?.let {
            return it
        } ?: run {
            return null
        }
    }

    override suspend fun loginWithUserName(userName: String, password: String): User? {
        userDao.getUserByUserNameAndPassword(userName, password)?.let {
            return it
        } ?: run {
            return null
        }
    }


}