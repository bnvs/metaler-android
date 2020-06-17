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

    override fun getSearchViewCategoryTypeCache(
        onSuccess: (categoryType: String) -> Unit,
        onFailure: () -> Unit
    ) {
        val categoryType = shared.getString(SEARCH_VIEW_CATEGORY_TYPE, null)
        if (categoryType != null) {
            onSuccess(categoryType)
        } else {
            onFailure()
        }
    }

    override fun saveSearchViewCategoryTypeCache(categoryType: String) {
        shared.edit().putString(SEARCH_VIEW_CATEGORY_TYPE, categoryType).commit()
    }
}