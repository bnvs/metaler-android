package com.bnvs.metaler.data.categories.source.remote

import com.bnvs.metaler.data.categories.source.CategoriesDataSource
import com.bnvs.metaler.network.RetrofitClient

object CategoriesRemoteDataSource : CategoriesDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getCatagories(callback: CategoriesDataSource.LoadCategoriesCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}