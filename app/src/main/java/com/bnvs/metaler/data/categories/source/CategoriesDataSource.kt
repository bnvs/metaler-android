package com.bnvs.metaler.data.categories.source

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesDataSource {

    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}