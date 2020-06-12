package com.bnvs.metaler.data.categories.source.repository

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesRepository {

    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    )

}