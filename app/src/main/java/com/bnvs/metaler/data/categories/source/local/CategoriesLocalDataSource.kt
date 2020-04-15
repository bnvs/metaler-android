package com.bnvs.metaler.data.categories.source.local

import android.content.Context
import com.bnvs.metaler.data.categories.Categories
import com.bnvs.metaler.data.categories.source.CategoriesDataSource

class CategoriesLocalDataSource (context: Context) : CategoriesDataSource {

    private val sharedPreferences =
        context.getSharedPreferences("LOCAL_CATEGORIES_DATA", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getCategories(
        onSuccess: (response: Categories) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}