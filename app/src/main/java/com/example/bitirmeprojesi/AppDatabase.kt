package com.example.bitirmeprojesi

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bitirmeprojesi.data.dao.CategoryDao
import com.example.bitirmeprojesi.data.dao.CameraDao
import com.example.bitirmeprojesi.data.dao.UserDao
import com.example.bitirmeprojesi.data.entitiy.Camera
import com.example.bitirmeprojesi.data.entitiy.Category
import com.example.bitirmeprojesi.data.entitiy.User

@Database(
    entities = [User::class, Category::class, Camera::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cameraDao(): CameraDao
}