package com.bnvs.metaler.data.categories.source.repository

import android.content.Context
import com.bnvs.metaler.data.categories.model.Categories
import com.bnvs.metaler.data.categories.source.CategoriesDataSource
import com.bnvs.metaler.data.categories.source.remote.CategoriesRemoteDataSource

class CategoriesRepository(context: Context) :
    CategoriesDataSource {

    private val categoriesRemoteDataSource = CategoriesRemoteDataSource

    override fun getCategories(
        onSuccess: (response: Categories) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        categoriesRemoteDataSource.getCategories(onSuccess, onFailure)
    }
}