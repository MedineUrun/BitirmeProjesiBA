package com.example.bitirmeprojesi.data.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Camera (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val filePath:String?,
)