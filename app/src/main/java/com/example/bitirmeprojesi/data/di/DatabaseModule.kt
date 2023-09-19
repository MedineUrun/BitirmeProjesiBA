package com.example.bitirmeprojesi.data.di

import android.content.Context
import androidx.room.Room
import com.example.bitirmeprojesi.AppDatabase
import com.example.bitirmeprojesi.Constants
import com.example.bitirmeprojesi.data.dao.CategoryDao
import com.example.bitirmeprojesi.data.dao.CameraDao
import com.example.bitirmeprojesi.data.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Provides
    @Singleton
    fun provideCameraDao(appDatabase: AppDatabase): CameraDao {
        return appDatabase.cameraDao()
    }

}