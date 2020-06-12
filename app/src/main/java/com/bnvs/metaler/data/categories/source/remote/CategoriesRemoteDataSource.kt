package com.bnvs.metaler.data.categories.source.remote

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesRemoteDataSource {
    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )
}