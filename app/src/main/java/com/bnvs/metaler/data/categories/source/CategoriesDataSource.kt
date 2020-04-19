package com.bnvs.metaler.data.categories.source

import com.bnvs.metaler.data.categories.model.Categories

interface CategoriesDataSource {

    fun getCategories(
        onSuccess: (response: Categories) -> Unit,
        onFailure: (e: Throwable) -> Unit
    )

}