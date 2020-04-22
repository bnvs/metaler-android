package com.bnvs.metaler.data.posts.source.remote

import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

object PostsRemoteDataSource : PostsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getPosts(
        request: PostsRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getPosts(getOptions(request)).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    onSuccess(body)
                } else {
                    onFailure(HttpException(response))
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                onFailure(t)
            }

        })
    }

    private fun getOptions(request: PostsRequest): MutableMap<String, Any> {
        return mutableMapOf<String, Any>(
            "category_id" to request.category_id,
            "page" to request.page,
            "limit" to request.limit
        ).apply {
            if (request.search_type != null && request.search_type.isNotEmpty()) {
                put("search_type", request.search_type)
            }
            if (request.search_word != null && request.search_word.isNotEmpty()) {
                put("search_word", request.search_word)
            }
        }
    }
}