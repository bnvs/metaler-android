package com.bnvs.metaler_android.data.categories.source.remote

import com.bnvs.metaler_android.data.categories.source.CategoriesDataSource
import com.bnvs.metaler_android.network.RetrofitClient

object CategoriesRemoteDataSource : CategoriesDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getCatagories(callback: CategoriesDataSource.LoadCategoriesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}