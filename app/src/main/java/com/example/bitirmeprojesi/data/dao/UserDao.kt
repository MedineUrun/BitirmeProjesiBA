package com.example.bitirmeprojesi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bitirmeprojesi.data.entitiy.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User):Long

    @Query("SELECT * FROM user WHERE email = :email  AND password = :password")
    suspend fun getUser(email: String, password: String): User?

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM User WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM User WHERE userName = :userName")
    suspend fun getUserByUserName(userName: String): User?

    @Query("SELECT * FROM User WHERE userName = :userName OR email = :email")
    suspend fun getUserByUserNameOrEmail(userName: String, email: String): User?

    @Query("SELECT * FROM User WHERE userName = :userName AND email = :email AND password = :password")
    suspend fun getUserByUserNameAndEmailAndPassword(userName: String, email: String, password:String): User?

    @Query("SELECT * FROM User WHERE userName = :userName AND email = :email")
    suspend fun getUserByUserNameAndEmail(userName: String, email: String): User?

    @Query("SELECT * FROM user WHERE (email = :userInfo OR userName = :userInfo ) AND password = :password")
    suspend fun getUserByUserInfoAndPassword(userInfo: String, password: String): User?

    @Query("SELECT * FROM User WHERE userName = :userName AND password = :password")
    suspend fun getUserByUserNameAndPassword(userName: String, password: String): User?

    @Query("SELECT * FROM User WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

}