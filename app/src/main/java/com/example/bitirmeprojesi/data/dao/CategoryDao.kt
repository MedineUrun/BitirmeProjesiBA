package com.example.bitirmeprojesi.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitirmeprojesi.data.entitiy.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: Category):Long

    @Query("SELECT categoryName FROM Category")
    suspend fun getCategories() : List<String>

    @Query("SELECT * FROM Category WHERE categoryName= :categoryName")
    suspend fun getCategoryByCategoryName(categoryName:String):Category


}


