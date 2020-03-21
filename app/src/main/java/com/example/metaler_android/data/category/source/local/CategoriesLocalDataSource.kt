package com.example.metaler_android.data.category.source.local

import android.content.Context
import com.example.metaler_android.data.category.source.CategoryDataSource

class CategoriesLocalDataSource (context: Context) : CategoryDataSource {

    private val sharedPreferences = context.getSharedPreferences("categories", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getCatagories(callback: CategoryDataSource.LoadCategoriesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}