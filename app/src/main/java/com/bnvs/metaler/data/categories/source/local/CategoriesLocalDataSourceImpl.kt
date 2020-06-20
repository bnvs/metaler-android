package com.bnvs.metaler.data.categories.source.local

import android.content.Context
import com.bnvs.metaler.data.categories.model.Categories
import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.util.constants.CATEGORIES_DATA
import com.bnvs.metaler.util.constants.LOCAL_CATEGORIES_DATA
import com.bnvs.metaler.util.constants.SEARCH_VIEW_CATEGORY_TYPE
import com.google.gson.GsonBuilder

class CategoriesLocalDataSourceImpl(context: Context) : CategoriesLocalDataSource {

    private val shared =
        context.getSharedPreferences(LOCAL_CATEGORIES_DATA, Context.MODE_PRIVATE)

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: () -> Unit
    ) {
        val categories = shared.getString(CATEGORIES_DATA, null)
        if (categories != null) {
            onSuccess(
                GsonBuilder().create().fromJson(categories, Categories::class.java).categories
            )
        } else {
            onFailure()
        }
    }

    override fun saveCategories(categories: List<Category>) {
        shared.edit()
            .putString(CATEGORIES_DATA, GsonBuilder().create().toJson(Categories(categories)))
            .commit()
    }

    override fun getCategoryTypeCache(
        onSuccess: (categoryType: Int) -> Unit,
        onFailure: () -> Unit
    ) {
        val categoryType: Int = shared.getInt(SEARCH_VIEW_CATEGORY_TYPE, -1)
        if (categoryType != -1) {
            onSuccess(categoryType)
        } else {
            onFailure()
        }
    }

    override fun saveCategoryTypeCache(categoryType: Int) {
        shared.edit().putInt(SEARCH_VIEW_CATEGORY_TYPE, categoryType).commit()
    }
}