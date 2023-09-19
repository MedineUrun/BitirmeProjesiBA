package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.entitiy.Category

interface CategoryRepository {

    suspend fun insert(category: Category):Long
    suspend fun getCategories() : List<String>
    suspend fun getCategoryByCategoryName(categoryName:String):Category
}