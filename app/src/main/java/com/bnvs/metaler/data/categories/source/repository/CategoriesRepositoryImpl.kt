package com.bnvs.metaler.data.categories.source.repository

import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.local.CategoriesLocalDataSource
import com.bnvs.metaler.data.categories.source.remote.CategoriesRemoteDataSource

class CategoriesRepositoryImpl(
    private val categoriesLocalDataSource: CategoriesLocalDataSource,
    private val categoriesRemoteDataSource: CategoriesRemoteDataSource
) : CategoriesRepository {

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        categoriesRemoteDataSource.getCategories(onSuccess, onFailure, handleError)
    }
}