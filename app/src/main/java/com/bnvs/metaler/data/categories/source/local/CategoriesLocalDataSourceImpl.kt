package com.bnvs.metaler.data.categories.source.local

import android.content.Context
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.util.constants.LOCAL_CATEGORIES_DATA

class CategoriesLocalDataSourceImpl(context: Context) : CategoriesLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_CATEGORIES_DATA, Context.MODE_PRIVATE)

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {

    }
}