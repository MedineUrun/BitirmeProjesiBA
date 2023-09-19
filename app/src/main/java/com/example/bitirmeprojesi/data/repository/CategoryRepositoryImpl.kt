package com.example.bitirmeprojesi.data.repository

import com.example.bitirmeprojesi.data.dao.CategoryDao
import com.example.bitirmeprojesi.data.entitiy.Category
import javax.inject.Inject


class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
):CategoryRepository {
    override suspend fun insert(category: Category): Long = categoryDao.insert(category)?.let {
    return it
    }?: run{
        return -2
    }

    override suspend fun getCategories(): List<String> {
        return categoryDao.getCategories()
    }

    override suspend fun getCategoryByCategoryName(categoryName: String): Category {
        return categoryDao.getCategoryByCategoryName(categoryName)
    }
}