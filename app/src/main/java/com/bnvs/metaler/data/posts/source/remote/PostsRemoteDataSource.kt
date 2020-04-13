package com.bnvs.metaler.data.posts.source.remote

import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostsRemoteDataSource : PostsDataSource {

    private val retrofitClient = RetrofitClient.client

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostsDataSource.LoadPostsCallback
    ) {
        val options = mutableMapOf<String, Any>(
            "category_type" to request.category_type,
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

        retrofitClient.getPosts(access_token, options).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                if (response.isSuccessful) {
                    callback.onPostsLoaded(response.body()!!)
                } else {
                    callback.onResponseError(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }
}