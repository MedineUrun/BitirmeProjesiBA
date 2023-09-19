package com.example.bitirmeprojesi.data.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    var userName:String?,
    var email: String?,
    var password: String?,

    )
