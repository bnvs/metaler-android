package com.bnvs.metaler.data.categories.source

import android.content.Context
import com.bnvs.metaler.data.categories.Categories
import com.bnvs.metaler.data.categories.source.remote.CategoriesRemoteDataSource

class CategoriesRepository(context: Context) : CategoriesDataSource {

    private val categoriesRemoteDataSource = CategoriesRemoteDataSource

    override fun getCategories(
        onSuccess: (response: Categories) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        categoriesRemoteDataSource.getCategories(onSuccess, onFailure)
    }
}