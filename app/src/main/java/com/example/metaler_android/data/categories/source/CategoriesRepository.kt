package com.example.metaler_android.data.categories.source

import android.content.Context
import com.example.metaler_android.data.categories.source.local.CategoriesLocalDataSource
import com.example.metaler_android.data.categories.source.remote.CategoriesRemoteDataSource

class CategoriesRepository(context: Context) : CategoriesDataSource {

    private val categoriesLocalDataSource = CategoriesLocalDataSource(context)
    private val categoriesRemoteDataSource = CategoriesRemoteDataSource

    override fun getCatagories(callback: CategoriesDataSource.LoadCategoriesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}