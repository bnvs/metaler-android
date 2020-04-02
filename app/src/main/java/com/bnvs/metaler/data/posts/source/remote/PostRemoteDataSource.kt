package com.bnvs.metaler.data.posts.source.remote

import com.bnvs.metaler.data.posts.PostsRequest
import com.bnvs.metaler.data.posts.PostsResponse
import com.bnvs.metaler.data.posts.source.PostDataSource
import com.bnvs.metaler.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PostRemoteDataSource : PostDataSource{

    private val retrofitClient = RetrofitClient.client

    override fun getPosts(
        access_token: String,
        request: PostsRequest,
        callback: PostDataSource.LoadPostsCallback
    ) {
        retrofitClient.getPosts(access_token, request).enqueue(object : Callback<PostsResponse> {
            override fun onResponse(call: Call<PostsResponse>, response: Response<PostsResponse>) {
                val postsResponse: PostsResponse? = response.body()
                if (postsResponse != null) {
                    callback.onPostsLoaded(postsResponse)
                }else {
                    callback.onDataNotAvailable()
                }
            }

            override fun onFailure(call: Call<PostsResponse>, t: Throwable) {
            }

        })
    }
}