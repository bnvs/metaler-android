package com.bnvs.metaler.data.categories.source.local

import com.bnvs.metaler.data.categories.model.Category

interface CategoriesLocalDataSource {
    fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )
}