package com.bnvs.metaler.data.categories.source.remote

import com.bnvs.metaler.data.categories.model.Category
import com.bnvs.metaler.network.ErrorHandler
import com.bnvs.metaler.network.RetrofitInterface
import com.bnvs.metaler.util.constants.NO_ERROR_TO_HANDLE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class CategoriesRemoteDataSourceImpl(
    private val retrofitClient: RetrofitInterface
) : CategoriesRemoteDataSource {

    override fun getCategories(
        onSuccess: (response: List<Category>) -> Unit,
        onFailure: (e: Throwable) -> Unit,
        handleError: (errorCode: Int) -> Unit
    ) {
        retrofitClient.getCategories().enqueue(object : Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    val e = ErrorHandler.getErrorCode(HttpException(response))
                    if (e != NO_ERROR_TO_HANDLE) {
                        handleError(e)
                    }
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                onFailure(t)
            }
        })
    }
}