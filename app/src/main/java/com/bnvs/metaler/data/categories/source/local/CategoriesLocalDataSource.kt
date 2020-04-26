package com.bnvs.metaler.data.categories.source.local

import android.content.Context
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.data.categories.source.CategoriesDataSource

class CategoriesLocalDataSource(context: Context) : CategoriesDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_CATEGORIES_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}