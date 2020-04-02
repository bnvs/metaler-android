package com.bnvs.metaler.data.posts.source.remote

import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.posts.source.PostsDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostsRemoteDataSource : PostsDataSource{

    private val retrofitClient = RetrofitClient.client

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostsDataSource.LoadPostsCallback
    ) {
        retrofitClient.getPosts(access_token, request).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                if (response.isSuccessful) {
                    callback.onPostsLoaded(response.body()!!)
                }else {
                    callback.onResponseError(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }
}