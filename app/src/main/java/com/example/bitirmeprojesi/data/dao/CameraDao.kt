package com.example.bitirmeprojesi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.bitirmeprojesi.data.entitiy.Camera

@Dao
interface CameraDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(photo: Camera):Long

}