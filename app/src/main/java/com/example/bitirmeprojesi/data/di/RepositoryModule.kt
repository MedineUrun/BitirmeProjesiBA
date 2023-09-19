package com.example.bitirmeprojesi.data.di

import com.example.bitirmeprojesi.data.repository.CameraRepository
import com.example.bitirmeprojesi.data.repository.CameraRepositoryImpl
import com.example.bitirmeprojesi.data.repository.CategoryRepository
import com.example.bitirmeprojesi.data.repository.CategoryRepositoryImpl
import com.example.bitirmeprojesi.data.repository.LoginRepository
import com.example.bitirmeprojesi.data.repository.LoginRepositoryImpl
import com.example.bitirmeprojesi.data.repository.PhotoRepository
import com.example.bitirmeprojesi.data.repository.PhotoRepositoryImpl
import com.example.bitirmeprojesi.data.repository.ProfileRepository
import com.example.bitirmeprojesi.data.repository.ProfileRepositoryImpl
import com.example.bitirmeprojesi.data.repository.RegisterRepository
import com.example.bitirmeprojesi.data.repository.RegisterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository = loginRepositoryImpl

    @Provides
    @Singleton
    fun provideRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository = registerRepositoryImpl

     @Provides
     @Singleton
     fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository = categoryRepositoryImpl

    @Provides
    @Singleton
    fun providePhotoRepository(photoRepositoryImpl: PhotoRepositoryImpl): PhotoRepository = photoRepositoryImpl

    @Provides
    @Singleton
    fun provideProfileUpdate(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository = profileRepositoryImpl

    @Provides
    @Singleton
    fun provideCamera(cameraRepositoryImpl: CameraRepositoryImpl): CameraRepository = cameraRepositoryImpl
}