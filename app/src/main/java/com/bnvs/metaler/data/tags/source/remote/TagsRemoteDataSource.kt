package com.bnvs.metaler.data.tags.source.remote

import com.bnvs.metaler.data.tags.model.TagsRequest
import com.bnvs.metaler.data.tags.source.TagsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object TagsRemoteDataSource : TagsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getTagRecommendations(
        request: TagsRequest,
        onSuccess: (response: List<String>) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getTagRecommendation(request.type, request.name, request.max)
            .enqueue(object :
                Callback<List<String>> {
                override fun onResponse(
                    call: Call<List<String>>,
                    response: Response<List<String>>
                ) {
                    val body = response.body()
                    if (body != null && response.isSuccessful) {
                        onSuccess(body)
                    } else {
                        onFailure(HttpException(response))
                    }
                }

                override fun onFailure(call: Call<List<String>>, t: Throwable) {
                    onFailure(t)
                }
            })
    }
}