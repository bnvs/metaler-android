package com.bnvs.metaler.data.categories.source.remote

import com.bnvs.metaler.data.categories.model.Categories
import com.bnvs.metaler.data.categories.source.CategoriesDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object CategoriesRemoteDataSource : CategoriesDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getCategories(
        onSuccess: (response: Categories) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getCategories().enqueue(object : Callback<Categories> {
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}