package com.bnvs.metaler.data.categories.source.repository

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesRepository {

    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun getCategoriesFromRemote(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

    fun saveCategories(categories: List<Category>)

    fun getSearchViewCategoryTypeCache(
        onSuccess: (categoryType: String) -> Unit,
        onFailure: () -> Unit
    )

    fun saveSearchViewCategoryTypeCache(categoryType: String)

}