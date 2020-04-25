package com.bnvs.metaler.data.categories.source.repository

import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.CategoriesDataSource
import com.bnvs.metaler.data.categories.source.remote.CategoriesRemoteDataSource

class CategoriesRepository :
    CategoriesDataSource {

    private val categoriesRemoteDataSource = CategoriesRemoteDataSource

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        categoriesRemoteDataSource.getCategories(onSuccess, onFailure)
    }
}