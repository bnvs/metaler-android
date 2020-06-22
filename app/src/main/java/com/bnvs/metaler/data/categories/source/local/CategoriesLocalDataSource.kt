package com.bnvs.metaler.data.categories.source.local

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesLocalDataSource {

    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: () -> Unit
    )

    fun saveCategories(categories: List<Category>)

    fun getCategoryTypeCache(
        onSuccess: (categoryType: Int) -> Unit,
        onFailure: () -> Unit
    )

    fun saveCategoryTypeCache(categoryType: Int)
}