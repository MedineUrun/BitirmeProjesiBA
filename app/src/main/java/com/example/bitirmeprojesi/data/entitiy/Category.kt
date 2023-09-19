package com.example.bitirmeprojesi.data.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val categoryName:String?,
)
