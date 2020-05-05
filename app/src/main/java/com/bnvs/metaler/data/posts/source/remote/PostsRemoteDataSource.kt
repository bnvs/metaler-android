package com.bnvs.metaler.data.posts.source.remote

import com.bnvs.metaler.data.posts.model.PostsRequest
import com.bnvs.metaler.data.posts.model.PostsResponse
import com.bnvs.metaler.data.posts.model.PostsWithContentRequest
import com.bnvs.metaler.data.posts.model.PostsWithTagRequest
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

    override fun getPostsWithSearchTypeContent(
        request: PostsWithContentRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getPosts(getContentOptions(request))
            .enqueue(object : Callback<PostsResponse> {
                override fun onResponse(
                    call: Call<PostsResponse>,
                    response: Response<PostsResponse>
                ) {
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

    override fun getPostsWithSearchTypeTag(
        request: PostsWithTagRequest,
        onSuccess: (response: PostsResponse) -> Unit,
        onFailure: (e: Throwable) -> Unit
    ) {
        retrofitClient.getPosts(geTagOptions(request)).enqueue(object : Callback<PostsResponse> {
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
        return mutableMapOf(
            "category_id" to request.category_id,
            "page" to request.page,
            "limit" to request.limit
        )
    }

    private fun getContentOptions(request: PostsWithContentRequest): MutableMap<String, Any> {
        return mutableMapOf(
            "category_id" to request.category_id,
            "page" to request.page,
            "limit" to request.limit,
            "search_type" to request.search_type,
            "search_word" to request.search_word
        )
    }

    private fun geTagOptions(request: PostsWithTagRequest): MutableMap<String, Any> {
        return mutableMapOf(
            "category_id" to request.category_id,
            "page" to request.page,
            "limit" to request.limit,
            "search_type" to request.search_type,
            "search_word" to request.search_word
        )
    }
}