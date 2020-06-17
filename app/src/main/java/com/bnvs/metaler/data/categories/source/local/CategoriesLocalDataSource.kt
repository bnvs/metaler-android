package com.bnvs.metaler.data.categories.source.local

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesLocalDataSource {

    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: () -> Unit
    )

    fun saveCategories(categories: List<Category>)

    fun getSearchViewCategoryTypeCache(
        onSuccess: (categoryType: String) -> Unit,
        onFailure: () -> Unit
    )

    fun saveSearchViewCategoryTypeCache(categoryType: String)
}