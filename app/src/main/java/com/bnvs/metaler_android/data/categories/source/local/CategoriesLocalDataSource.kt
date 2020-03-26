package com.bnvs.metaler_android.data.categories.source.local

import android.content.Context
import com.bnvs.metaler_android.data.categories.source.CategoriesDataSource

class CategoriesLocalDataSource (context: Context) : CategoriesDataSource {

    private val sharedPreferences = context.getSharedPreferences("categories", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    override fun getCatagories(callback: CategoriesDataSource.LoadCategoriesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}